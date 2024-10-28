rm PropertyFindByIDLambda.txt
rm PropertySaveLambda.txt
mvn package

sam deploy \
--s3-bucket pburskey-home \
--stack-name PropertyConfiguration \
--capabilities CAPABILITY_IAM


./aws_build_url_for_function.sh PropertyFindByIDLambda / 2>&1 | tee PropertyFindByIDLambda.txt
./aws_build_url_for_function.sh PropertySaveLambda /save 2>&1 | tee PropertySaveLambda.txt
