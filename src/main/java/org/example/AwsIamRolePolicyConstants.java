package org.example;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/* This is helper class to validate Policy Constants.
*  Information about various constants could be found there:
*  https://docs.aws.amazon.com/IAM/latest/UserGuide/access_policies.html#access_policies-json
*  https://docs.aws.amazon.com/AWSCloudFormation/latest/UserGuide/aws-properties-iam-role-policy.html */
public class AwsIamRolePolicyConstants {
    public static final int POLICY_NAME_MINIMUM_LENGTH = 1;
    public static final int POLICY_NAME_MAXIMUM_LENGTH = 128;
    public static final Pattern PolicyNamePattern = Pattern.compile("[\\w+=,.@-]+");
    public static final List<String> validEffectForPolicy = Arrays.asList("Allow", "Deny");
    public static final List<String> validStatementElements = Arrays.asList("Sid", "Effect", "Principal", "Action", "Resource", "Condition");
    public static final List<String> validRolePolicyProperties = Arrays.asList("PolicyDocument", "PolicyName");
    public static final List<String> validPolicyDocumentElements = Arrays.asList("Version", "Statement");

}
