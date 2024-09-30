import com.api.jsonata4java.Expression;
import com.api.jsonata4java.expressions.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;

public class JsonValidationExample {

    public static void main(String[] args) throws Exception {
        String jsonInput = "{ \"user\": { \"firstName\": \"John\", \"lastName\": \"Doe\", \"age\": 17, \"email\": \"john.doe@example.com\", \"address\": { \"city\": \"New York\", \"country\": \"USA\" } } }";
        
        // Parse the input JSON
        JsonNode inputNode = JsonUtils.jsonToNode(jsonInput);
        
        // JSONata expression to validate the input JSON
        String validationExpression = "{ " +
            "\"isValid\": $and(" +
            "  $exists(user.firstName) and $type(user.firstName) = 'string', " +
            "  $exists(user.lastName) and $type(user.lastName) = 'string', " +
            "  $exists(user.age) and $type(user.age) = 'number' and user.age > 18, " +
            "  $exists(user.email) and $type(user.email) = 'string', " +
            "  $exists(user.address.city) and $exists(user.address.country)" +
            "), " +
            "\"errorMessage\": $not($exists(user.firstName)) ? 'First name is missing' : " +
            "$not($exists(user.lastName)) ? 'Last name is missing' : " +
            "$not($exists(user.age)) ? 'Age is missing' : " +
            "$type(user.age) != 'number' ? 'Age must be a number' : " +
            "user.age <= 18 ? 'Age must be greater than 18' : " +
            "$not($exists(user.email)) ? 'Email is missing' : " +
            "$not($exists(user.address.city)) ? 'City is missing' : " +
            "$not($exists(user.address.country)) ? 'Country is missing' : " +
            "null }";
        
        // Compile and evaluate the expression
        Expression jsonataExpr = Expression.jsonata(validationExpression);
        JsonNode resultNode = jsonataExpr.evaluate(inputNode);
        
        // Output the validation result
        System.out.println(JsonUtils.toJsonString(resultNode));
    }
}
