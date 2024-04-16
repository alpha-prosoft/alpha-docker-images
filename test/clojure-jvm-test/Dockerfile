ARG BUILD_ID
ARG DOCKER_URL
ARG DOCKER_ORG

ARG PROJECT_NAME=clojure-native-image-test
ARG MAIN_CLASS=jvmtest.main
ARG BUILD_TARGET=DEV

FROM ${DOCKER_URL}/${DOCKER_ORG}/cljjava-img:b${BUILD_ID}

RUN java -jar /dist/${PROJECT_NAME}.zip
CMD java -jar /dist/${PROJECT_NAME}.zip

