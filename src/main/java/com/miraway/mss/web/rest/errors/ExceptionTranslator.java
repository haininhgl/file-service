package com.miraway.mss.web.rest.errors;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import com.miraway.mss.modules.common.exception.*;
import com.miraway.mss.web.rest.responses.APIResponse;
import feign.FeignException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.zalando.problem.*;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.spring.web.advice.security.SecurityAdviceTrait;
import org.zalando.problem.violations.ConstraintViolationProblem;
import org.zalando.problem.violations.Violation;
import tech.jhipster.config.JHipsterConstants;
import tech.jhipster.web.util.HeaderUtil;

/**
 * Controller advice to translate the server side exceptions to client-friendly json structures.
 * The error response follows RFC7807 - Problem Details for HTTP APIs (https://tools.ietf.org/html/rfc7807).
 */
@ControllerAdvice
public class ExceptionTranslator implements ProblemHandling, SecurityAdviceTrait {

    private static final String FIELD_ERRORS_KEY = "fieldErrors";
    private static final String MESSAGE_KEY = "message";
    private static final String PATH_KEY = "path";
    private static final String VIOLATIONS_KEY = "violations";
    private static final String CODE_KEY = "code";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final Environment env;

    public ExceptionTranslator(Environment env) {
        this.env = env;
    }

    /**
     * Post-process the Problem payload to add the message key for the front-end if needed.
     */
    @Nullable
    @Override
    public ResponseEntity<Problem> process(@Nullable ResponseEntity<Problem> entity, @Nonnull NativeWebRequest request) {
        if (entity == null) {
            return null;
        }

        Problem problem = entity.getBody();
        if (!(problem instanceof ConstraintViolationProblem || problem instanceof DefaultProblem)) {
            return entity;
        }

        HttpServletRequest nativeRequest = request.getNativeRequest(HttpServletRequest.class);
        String requestUri = nativeRequest != null ? nativeRequest.getRequestURI() : StringUtils.EMPTY;
        ProblemBuilder builder = Problem
            .builder()
            .withType(Problem.DEFAULT_TYPE.equals(problem.getType()) ? ErrorConstants.DEFAULT_TYPE : problem.getType())
            .withStatus(Status.OK)
            .with(CODE_KEY, problem.getStatus())
            .withTitle(problem.getTitle())
            .with(PATH_KEY, requestUri);

        if (problem instanceof ConstraintViolationProblem) {
            builder
                .with(VIOLATIONS_KEY, ((ConstraintViolationProblem) problem).getViolations())
                // TODO: multi language
                .with(MESSAGE_KEY, "Tham số không hợp lệ.");
        } else {
            builder.withCause(((DefaultProblem) problem).getCause()).withDetail(problem.getDetail()).withInstance(problem.getInstance());
            problem.getParameters().forEach(builder::with);
            if (!problem.getParameters().containsKey(MESSAGE_KEY) && problem.getStatus() != null) {
                // TODO: multi language
                builder.with(MESSAGE_KEY, "Có lỗi xảy ra.");
            }
        }
        return new ResponseEntity<>(builder.build(), entity.getHeaders(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Problem> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @Nonnull NativeWebRequest request) {
        BindingResult result = ex.getBindingResult();
        List<FieldErrorVM> fieldErrors = result
            .getFieldErrors()
            .stream()
            .map(f ->
                new FieldErrorVM(
                    f.getObjectName().replaceFirst("DTO$", ""),
                    f.getField(),
                    StringUtils.isNotBlank(f.getDefaultMessage()) ? f.getDefaultMessage() : f.getCode()
                )
            )
            .collect(toList());

        Problem problem = Problem
            .builder()
            .withType(ErrorConstants.CONSTRAINT_VIOLATION_TYPE)
            .withTitle("Method argument not valid")
            .withStatus(Status.OK)
            .with(CODE_KEY, HttpStatus.BAD_REQUEST.value())
            // TODO: multi language
            .with(MESSAGE_KEY, "Tham số không hợp lệ.")
            .with(FIELD_ERRORS_KEY, fieldErrors)
            .build();
        return create(ex, problem, request);
    }

    @Override
    public ResponseEntity<Problem> newConstraintViolationProblem(
        @Nonnull Throwable throwable,
        Collection<Violation> stream,
        @Nonnull NativeWebRequest request
    ) {
        URI type = defaultConstraintViolationType();
        StatusType status = Status.OK;

        List<Violation> violations = stream
            .stream()
            // sorting to make tests deterministic
            .sorted(comparing(Violation::getField).thenComparing(Violation::getMessage))
            .collect(toList());

        Problem problem = Problem
            .builder()
            .withType(type)
            .withTitle("Constraint Violation")
            .withStatus(status)
            .with(CODE_KEY, HttpStatus.BAD_REQUEST.value())
            // TODO: multi language
            .with(MESSAGE_KEY, "Vi phạm ràng buộc dữ liệu.")
            .with(VIOLATIONS_KEY, violations)
            .build();

        return create(throwable, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleBadRequestAlertException(BadRequestAlertException ex, NativeWebRequest request) {
        return create(
            ex,
            request,
            HeaderUtil.createFailureAlert(applicationName, true, ex.getEntityName(), ex.getErrorKey(), ex.getMessage())
        );
    }

    @ExceptionHandler
    public ResponseEntity<Problem> handleConcurrencyFailure(ConcurrencyFailureException ex, NativeWebRequest request) {
        Problem problem = Problem.builder().withStatus(Status.CONFLICT).with(MESSAGE_KEY, ErrorConstants.ERR_CONCURRENCY_FAILURE).build();
        return create(ex, problem, request);
    }

    @ExceptionHandler
    public ResponseEntity<APIResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        String message;
        if (ex.getMessage() != null) {
            message = ex.getMessage();
        } else {
            message = "";
        }

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.newFailureResponse(HttpStatus.NOT_FOUND.value(), message));
    }

    @ExceptionHandler
    public ResponseEntity<APIResponse> handleResourceBusyException(ResourceBusyException ex) {
        String message;
        if (ex.getMessage() != null) {
            message = ex.getMessage();
        } else {
            message = "";
        }

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.newFailureResponse(HttpStatus.CONFLICT.value(), message));
    }

    @ExceptionHandler
    public ResponseEntity<APIResponse> handleBadRequestException(BadRequestException ex) {
        String message;
        if (ex.getMessage() != null) {
            message = ex.getMessage();
        } else {
            message = "";
        }

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.newFailureResponse(message));
    }

    @ExceptionHandler
    public ResponseEntity<APIResponse> handleCommonException(InternalServerException ex) {
        String message;
        if (ex.getMessage() != null) {
            message = ex.getMessage();
        } else {
            message = "";
        }

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.newFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), message));
    }

    @ExceptionHandler
    public ResponseEntity<APIResponse> handleParseDataException(ParseDataException ex) {
        String message;
        if (ex.getMessage() != null) {
            message = ex.getMessage();
        } else {
            message = "";
        }

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.newFailureResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), message));
    }

    @ExceptionHandler
    public ResponseEntity<APIResponse> handleForbiddenException(ForbiddenException ex) {
        String message;
        if (ex.getMessage() != null) {
            message = ex.getMessage();
        } else {
            message = "";
        }

        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.newFailureResponse(HttpStatus.FORBIDDEN.value(), message));
    }

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<APIResponse> handleFeignStatusException(FeignException e, HttpServletResponse response) {
        response.setStatus(e.status());
        return ResponseEntity.status(HttpStatus.OK).body(APIResponse.newFailureResponse(HttpStatus.FORBIDDEN.value(), "Lỗi server"));
    }

    @Override
    public ProblemBuilder prepare(final Throwable throwable, final StatusType status, final URI type) {
        Collection<String> activeProfiles = Arrays.asList(env.getActiveProfiles());

        if (activeProfiles.contains(JHipsterConstants.SPRING_PROFILE_PRODUCTION)) {
            if (throwable instanceof HttpMessageConversionException) {
                return Problem
                    .builder()
                    .withType(type)
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Unable to convert http message")
                    .withCause(
                        Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
                    );
            }
            if (throwable instanceof DataAccessException) {
                return Problem
                    .builder()
                    .withType(type)
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Failure during data access")
                    .withCause(
                        Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
                    );
            }
            if (containsPackageName(throwable.getMessage())) {
                return Problem
                    .builder()
                    .withType(type)
                    .withTitle(status.getReasonPhrase())
                    .withStatus(status)
                    .withDetail("Unexpected runtime exception")
                    .withCause(
                        Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
                    );
            }
        }

        return Problem
            .builder()
            .withType(type)
            .withTitle(status.getReasonPhrase())
            .withStatus(status)
            .withDetail(throwable.getMessage())
            .withCause(
                Optional.ofNullable(throwable.getCause()).filter(cause -> isCausalChainsEnabled()).map(this::toProblem).orElse(null)
            );
    }

    private boolean containsPackageName(String message) {
        // This list is for sure not complete
        return StringUtils.containsAny(message, "org.", "java.", "net.", "javax.", "com.", "io.", "de.", "com.miraway.mss");
    }
}
