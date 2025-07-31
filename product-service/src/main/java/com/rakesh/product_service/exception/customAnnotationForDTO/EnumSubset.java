package com.rakesh.product_service.exception.customAnnotationForDTO;

import com.rakesh.product_service.entity.ProductStatus;
import com.rakesh.product_service.exception.customAnnotationForDTO.EnumSubsetValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EnumSubsetValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumSubset {
    ProductStatus[] anyOf(); // Allowed values
    String message() default "must be one of {anyOf}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
