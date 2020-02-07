# scrambls

Backend and frontend performing scramble.

Type two strings to check if they are scramble i.e. if a portion of str1 characters can be rearranged to match str2.

Frontend does POST request to the backend that validates both strings and checks for scramble.

## Running

To start a web server for the application, run:

    lein build-dev


## Testing

    lein test

### Curl tests 

curl --header "Content-Type: application/json"  --request POST --data '{"str1":"rekqodlw","str2":"world"}' 'localhost:9500/api/scramble'

curl --header "Content-Type: application/json"  --request POST --data '{"str1":"cedewaraaossoqqyt","str2":"codewars"}' 'localhost:9500/api/scramble'

curl --header "Content-Type: application/json"  --request POST --data '{"str1":"katas","str2":"steak"}' 'localhost:9500/api/scramble'

curl --header "Content-Type: application/json"  --request POST --data '{"str1":"cedewaraaossoqqyt","str2":""}' 'localhost:9500/api/scramble'