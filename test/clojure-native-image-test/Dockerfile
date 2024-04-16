ARG BUILD_ID
ARG DOCKER_URL
ARG DOCKER_ORG

ARG PROJECT_NAME=clojure-native-image-test
ARG MAIN_CLASS=nativetest.main
ARG BUILD_TARGET=DEV

FROM ${DOCKER_URL}/${DOCKER_ORG}/clj-img:b${BUILD_ID}

RUN /dist/lambda_function
CMD /dist/lambda_function

