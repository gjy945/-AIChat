FROM openjdk:17.0.2


RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

RUN mkdir -p /opt/projects/myProject

WORKDIR /opt/projects/myProject

ADD ./admin/target/admin-0.0.1-SNAPSHOT.jar ./

EXPOSE 8085

CMD java -jar -Xms512m -Xmx512m -Xss256k -XX:SurvivorRatio=8 -Dspring.profiles.active=docker yami-shop-admin-0.0.1-SNAPSHOT.jar
