# memorygame-swarm
## Webes Memória játék
### Tartalmaz javaee példákat, úgy mint Dependency Injection, JPA with multiple persistence units, Repository-k (Delta-spike), exception kezelés, quartz, stb.
### Lásd még a további branch-eket: multiple-persistence-units, triple-persistence-units, triple-persistence-units-java11, triple-persistence-units-java11-xa.

Rendszerfeltételek:

A program 8-as Javával futtatható. Maven: 3.3.9 vagy 3.5.0

Adatbázis:
Oracle 10 XE (de 11-es adatbázis is megteszi)

A projektben levő com.oracle.ojdbc6 dependency-ről:
Ez local maven repóból való, vagyis a projektben található (a memorygame-swarm mappában).
A memorygame (webapp) projektben van, nemcsak a WAR, de a JAR file generálásához is szükséges.

A memorygame-common-entities projekt pom.xml-jében levő com.oracle.ojdbc6 dependency (ami provided), csak az esetleges Hibernate create entities from tables, vagy a HQL editorhoz kell, vagyis a program futtatásához nem.

Először hozzuk létre az MG user/schema-t (jelszó 'mg'), a createMGuser.sql segítségével.
A memorygame-swarm mappában futtasuk az "mvn install" parancsot.
Majd a memorygame-app/memorygame mappába lépve futtassuk az "mvn clean install -P liquibase" parancsot, amivel létrehozzuk a USERS és RESULT táblákat
/** Alternatív megoldás:
Hozzuk létre a táblákat a usersResultCreate.sql futtatásával.
Ez pl. SQLDeveloperben egyben lefuttatható,
de pl. az Oracle 10 Xe böngészős adatbáziskezelőjében (apex) a "/" jelekkel elválasztott részeket egyenként kell futtatni.
Utána az alapadatokat a csv (először a users, majd a result) file-okból importáljuk,
vagy a usereket regisztráció útján is létre lehet hozni. **/

Utána a memorygame mappában "mvn wildfly-swarm:package".
Ezután a memorygame/target mappából futtatható a java -jar memorygame-swarm.jar paranccsal.

Ezután az oldal elérhető a localhost:8484/memorygame url-en.

Wadl file információk: localhost:8484/memorygame/game/application.wadl

Jó játékot!
