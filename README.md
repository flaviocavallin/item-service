# Item Service example

./deploy.sh

----
Create serverless_user on AWS IAM


serverless config credentials --provider aws --key 1234 --secret 5678 --profile custom-profile

~/.aws/credentials
[profileName1]
aws_access_key_id=***************
aws_secret_access_key=***************


serverless create --template aws-java-gradle --path aws-java-gradle


serverless deploy --aws-profile devProfile
serverless remove --aws-profile devProfile

This deployment method does not touch your AWS CloudFormation Stack. Instead, it simply overwrites the zip file of the current function on AWS. This method is much faster, since it does not rely on CloudFormation.
serverless deploy function --function myFunction
-Note: You can use --update-config to change only Lambda configuration without deploying code.

serverless deploy function -f myFunction
serverless invoke -f myFunction -l (to test your AWS Lambda Functions on AWS)
serverless invoke local --function functionName --stage dev --region us-east-1 --data "hello world"  --log (test local)
serverless logs -f myFunction -t (stream logs)

Custom domain name:
https://www.serverless.com/blog/serverless-api-gateway-domain/


Genereate package.json file in your service. Run npm init -y

Lock down the framework version using package.json, then you can install Serverless as follows:
npm install serverless --save-dev
node ./node_modules/serverless/bin/serverless deploy


Lamdba endpoint example:
https://<lambda_id>.execute-api.<region>.amazonaws.com/<stage>/hello
