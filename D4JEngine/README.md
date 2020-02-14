
# to build djl dependencies locally

# create re-usable repo
docker volume create --name maven-repo


docker run -it --rm --name djl-repository-mvn -v maven-repo:/root/.m2 -v E:/git-gms/djl/repository/pom.xml:/git-gms/djl/repository/pom.xml -w /git-gms/djl/repository maven:3.6.3-jdk-11 mvn clean install package

docker run -it --rm --name djl-mvn -v maven-repo:/root/.m2 -v E:/git-gms/djl/model-zoo/pom.xml:/git-gms/djl/model-zoo/pom.xml -w /git-gms/djl/model-zoo maven:3.6.3-jdk-11 mvn clean install package