import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class EnumSubsetValidator implements ConstraintValidator<EnumSubset, ProductStatus> {

    private Set<ProductStatus> allowedValues;

    @Override
    public void initialize(EnumSubset constraintAnnotation) {
        allowedValues = Arrays.stream(constraintAnnotation.anyOf()).collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(ProductStatus value, ConstraintValidatorContext context) {
        return value == null || allowedValues.contains(value); // allow null; NotNull handles required
    }
}
