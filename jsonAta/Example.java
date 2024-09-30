
import com.api.jsonata4java.Expression;
import com.api.jsonata4java.expressions.utils.JsonUtils;
import com.fasterxml.jackson.databind.JsonNode;

public class ComplexJsonToNewStructure {

    public static void main(String[] args) throws Exception {
        String jsonInput = "{ \"organization\": { \"name\": \"TechCorp\", \"departments\": [" +
                           "{ \"name\": \"Engineering\", \"employees\": [" +
                           "{ \"firstName\": \"John\", \"lastName\": \"Doe\", \"age\": 30, \"address\": {\"city\": \"New York\", \"country\": \"USA\"}, \"skills\": [\"Java\", \"Python\"], \"salary\": 50000 }," +
                           "{ \"firstName\": \"Anna\", \"lastName\": \"Smith\", \"age\": 40, \"address\": {\"city\": \"London\", \"country\": \"UK\"}, \"skills\": [\"Python\", \"C++\"], \"salary\": 80000 }" +
                           "]}, { \"name\": \"Management\", \"employees\": [" +
                           "{ \"firstName\": \"Peter\", \"lastName\": \"Jones\", \"age\": 50, \"address\": {\"city\": \"Paris\", \"country\": \"France\"}, \"skills\": [\"Leadership\", \"Strategy\"], \"salary\": 100000 }" +
                           "]} ]}}";

        // Parse the input JSON
        JsonNode inputNode = JsonUtils.jsonToNode(jsonInput);
        
        // JSONata expression for complex transformation to new structure
        String expression = "{ " +
                            "\"organizationSummary\": { " +
                            "    \"orgName\": organization.name, " +
                            "    \"departments\": organization.departments.{ " +
                            "        \"departmentName\": name, " +
                            "        \"totalEmployees\": $count(employees), " +
                            "        \"employeeDetails\": employees.{ " +
                            "            \"fullName\": firstName & ' ' & lastName, " +
                            "            \"location\": address.city & ', ' & address.country, " +
                            "            \"mainSkill\": skills[0], " +
                            "            \"incomeBracket\": salary <= 50000 ? 'Low' : salary <= 80000 ? 'Medium' : 'High' " +
                            "        } " +
                            "    } " +
                            "} " +
                            "}";

        // Compile and evaluate the expression
        Expression jsonataExpr = Expression.jsonata(expression);
        JsonNode resultNode = jsonataExpr.evaluate(inputNode);

        // Output the transformed new JSON structure
        System.out.println(JsonUtils.toJsonString(resultNode));
    }
}
