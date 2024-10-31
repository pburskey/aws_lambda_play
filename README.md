# PropertyConfiguration

Creates and deploys an AWS Lambda function, implemented in Java.

## Prerequisites

* Created: An AWS account, and an S3 bucket for storing temporary deployment artifacts (referred to as $CF_BUCKET below)
* Installed: AWS CLI, Maven, SAM CLI
* Configured: AWS credentials in your terminal

## Usage

To build:

```
$ mvn package
```

To deploy:

```
$ sam deploy --s3-bucket $CF_BUCKET --stack-name guid --capabilities CAPABILITY_IAM
```

To test:

