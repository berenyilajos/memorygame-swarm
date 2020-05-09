# memorygame-swarm
## Webes Memória játék
### Tartalmaz javaee példákat, úgy mint Dependency Injection, JPA with multiple persistence units, Repository-k (Delta-spike), exception kezelés, quartz, stb.
### Ez a branch nem a swarm (kövér jar), hanem a Wildfly szerveren működő változat, kódban megegyezik a triple-persistence-units branch-el, csak a swarm-os dependency-k kikerültek belőle, valamint a project-default.xml is, ahelyett magában a Wildfly szerveren kell pár dolgot beállítani, lásd legalul

Rendszerfeltételek:

A program 8-as Javával futtatható. Maven: 3.3.9 vagy 3.5.0

Adatbázis:
Oracle 10 XE (de 11-es adatbázis is megteszi)

A projektben levő com.oracle.ojdbc6 dependency-ről:
Ez local maven repóból való, vagyis a projektben található (a memorygame-swarm mappában).
A memorygame (webapp) projektben van, a WAR file generálásához is szükséges.

A memorygame-common-entities projekt pom.xml-jében levő com.oracle.ojdbc6 dependency (ami provided), csak az esetleges Hibernate create entities from tables, vagy a HQL editorhoz kell, vagyis a program futtatásához nem.

Először hozzuk létre az MG user/schema-t (jelszó 'mg'), a createMGuser.sql segítségével.
Majd hozzuk létre az MGD user/schema-t (jelszó 'mgd'), a createMGDuser.sql segítségével.
A memorygame-swarm mappában futtasuk az "mvn install" parancsot.
Majd a memorygame-app/memorygame mappába lépve futtassuk az "mvn clean install -P liquibase" parancsot, amivel létrehozzuk a USERS és RESULT táblákat.
Majd a memorygame-app/memorygame mappába lépve futtassuk az "mvn clean install -P liquibase-data" parancsot, amivel létrehozzuk a RESULT_DATA táblát.
/** Alternatív megoldás:
Hozzuk létre a táblákat a usersResultCreate.sql futtatásával (MG user).
Hozzuk létre a ResultData táblát a resultDataCreate.sql futtatásával (MGD user).
Ez pl. SQLDeveloperben egyben lefuttatható,
de pl. az Oracle 10 Xe böngészős adatbáziskezelőjében (apex) a "/" jelekkel elválasztott részeket egyenként kell futtatni.
Utána az alapadatokat a csv (először a users, majd a result) file-okból importáljuk,
vagy a usereket regisztráció útján is létre lehet hozni. **/

A Wildfly szerverbe importáljuk az oracle drivert:
- ./bin/jboss-cli.sh --connect // értelemszerűen Windowson sh helyett bat
- module add --name=com.oracle --resources=/Users/shimul/Downloads/ojdbc7.jar --dependencies=javax.api,javax.transaction.api
- /subsystem=datasources/jdbc-driver=oracle:add(driver-name="oracle",driver-module-name="com.oracle",driver-class-name="oracle.jdbc.driver.OracleDriver",driver-xa-datasource-class-name="oracle.jdbc.xa.client.OracleXADataSource")

Esetleges eltávolítása:
- /subsystem=datasources/jdbc-driver=oracle:remove

A standalone-memorygame-xml.txt-ből a tartalmakat (logging-profile és datasource-ok) másoljuk át a Wildfly standalone-xml-jébe.

Majd vagy indítsuk konzolból a Wildfly szervert (a bin mappában található standalone.sh vagy bat) és standalone/deployments mappába másoljuk a memorygame.war-t, vagy indítsuk az IDE-ből (Eclipse, Idea) run on server (csak az Oracle adatbázissal együtt működik!!!).

Ezután az oldal elérhető a localhost:8383/memorygame url-en. // vagy a Wildfly-ban megadott url-en, a default 8080

Wadl file információk: localhost:8383/memorygame/game/application.wadl

Jó játékot!
