aws lambda invoke \
--function-name guid \
--invocation-type RequestResponse \
--cli-binary-format raw-in-base64-out \
--payload file://aws_invoke_payload.json \
response.json