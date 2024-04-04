package org.example;

import com.google.gson.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class JsonVerifier {

    private static boolean resourceWithAsterisk = true;

    public static boolean verifyJson(String pathToFile) {

        resourceWithAsterisk = true;

        try(BufferedReader reader = new BufferedReader(new FileReader(pathToFile))) {
            JsonElement jsonElement = JsonParser.parseReader(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            verifyEntries(jsonObject, AwsIamRolePolicyConstants.validRolePolicyProperties);
            verifyPolicyName(jsonObject.get("PolicyName"));
            verifyPolicyDocument(jsonObject.get("PolicyDocument"));

            JsonObject policyDocument = jsonObject.getAsJsonObject("PolicyDocument");
            if (!policyDocument.has("Statement"))
                throw new InvalidStructureException("[MESSAGE] Statement field is missing.\n");

            JsonElement statement = policyDocument.get("Statement");
            if(!(statement.isJsonArray() || statement.isJsonObject()))
                throw new InvalidStructureException("[MESSAGE] Statement is supposed to be array or json!.\n");

            if(statement.isJsonArray())
                for(JsonElement element : policyDocument.getAsJsonArray("Statement"))
                    verifyStatement(element.getAsJsonObject());
            else
                verifyStatement(policyDocument.getAsJsonObject("Statement"));


        } catch (InvalidStructureException e) {
            System.err.printf("[ERROR] Invalid structure %s", e.getMessage());
            return true;
        } catch (IOException e) {
            System.err.printf("[ERROR] file not found %s", e.getMessage() + "\n");
            return true;
        }
        return resourceWithAsterisk;
    }


    /* Function to validate if PolicyName meets requirements specified in AwsIamRolePolicyConstants,
     * ensuring it is a string.
     * If any invalid statement is encountered, it throws an InvalidStructureException. */
    private static void verifyPolicyName(JsonElement policyName) throws InvalidStructureException {
        if(policyName == null)
            throw new InvalidStructureException("[MESSAGE] PolicyName field is missing.\n");

        if(!policyName.isJsonPrimitive() || !((JsonPrimitive) policyName).isString())
            throw new InvalidStructureException("[MESSAGE] PolicyName is supposed to contain a single string value.\n");

        String policyNameValue = policyName.getAsString();

        if(policyNameValue.isEmpty())
            throw new InvalidStructureException("[MESSAGE] PolicyName does not meet MINIMUM_LENGTH requirement.\n");

        if(policyNameValue.length() > AwsIamRolePolicyConstants.POLICY_NAME_MAXIMUM_LENGTH)
            throw new InvalidStructureException("[MESSAGE] PolicyName does not meet MAXIMUM_LENGTH requirement.\n");

        if(!AwsIamRolePolicyConstants.PolicyNamePattern.matcher(policyNameValue).matches())
            throw new InvalidStructureException("[MESSAGE] PolicyName does not meet PATTERN requirement.\n");
    }

    /*
    * Function to validate if PolicyDocument meets requirements specified in AwsIamRolePolicyConstants,
    * ensuring it contains only valid elements and has all required elements.
    */
    private static void verifyPolicyDocument(JsonElement policyDocument) throws InvalidStructureException {
        if(policyDocument == null)
            throw new InvalidStructureException("[MESSAGE] PolicyDocument field is missing.\n");

        if(!policyDocument.isJsonObject())
            throw new InvalidStructureException("[MESSAGE] PolicyDocument is supposed to be json!.\n");

        if (!policyDocument.getAsJsonObject().has("Version"))
            throw new InvalidStructureException("[MESSAGE] \"Version\" is required.\n");

        JsonElement version = policyDocument.getAsJsonObject().get("Version");
        if(!version.isJsonPrimitive() || !((JsonPrimitive) version).isString())
            throw new InvalidStructureException("[MESSAGE] Version is supposed to contain a single string value.\n");

        verifyEntries(policyDocument.getAsJsonObject(), AwsIamRolePolicyConstants.validPolicyDocumentElements);
    }

    private static void verifyStatement(JsonObject statement) throws InvalidStructureException{
        /* Check if every entry from statement is valid */
        verifyEntries(statement, AwsIamRolePolicyConstants.validStatementElements);

        /* Effect is required in Statement */
        if (!statement.has("Effect"))
            throw new InvalidStructureException("[MESSAGE] \"Effect\" is required.\n");
        String effectValue = statement.get("Effect").getAsString();

        /* Effect should only have one of two valid values defined in AwsPolicyConstants */
        if(!AwsIamRolePolicyConstants.validEffectForPolicy.contains(effectValue))
            throw new InvalidStructureException("[MESSAGE] Use \"Allow\" or \"Deny\" to indicate whether the policy allows or denies access.\n");

        /* Action is also required in Statement */
        if (!statement.has("Action"))
            throw new InvalidStructureException("[MESSAGE] \"Action\" is required.\n");

        if(statement.has("Resource")){
            JsonElement resourceValue = statement.get("Resource");

            if(resourceValue.isJsonPrimitive()) {
                if (resourceValue.getAsString().equals("*"))
                    resourceWithAsterisk = false;
            } else {
                if (resourceValue.isJsonArray()){
                    for (JsonElement value : resourceValue.getAsJsonArray())
                        if (value.getAsString().equals("*"))
                            resourceWithAsterisk = false;
                } else
                    throw new InvalidStructureException("[ERROR] Resource should be String or Array.\n");
            }
        }
    }

    /* Function to check if every entry is valid.
    * If unknown entry is found then throw InvalidStructureException */
    private static void verifyEntries(JsonObject element, List<String> entries) throws InvalidStructureException {
        Set<String> keys = element.keySet();

        for (String elementName : keys)
            if (!entries.contains(elementName))
                throw new InvalidStructureException("[MESSAGE] Invalid Element: " + elementName + ".\n");
    }

}
