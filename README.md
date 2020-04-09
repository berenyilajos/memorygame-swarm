# memorygame-swarm
## Webes Memória játék
### Tartalmaz javaee példákat, úgy mint Dependency Injection, JPA with multiple persistence units, XA-Datasources with global transactions, Repository-k (Delta-spike), exception kezelés, quartz, stb.

Rendszerfeltételek:

A program 11-es Javával futtatható. Maven: 3.6.0 vagy újabb

Adatbázis:
Oracle 10 XE (de 11-es adatbázis is megteszi)

A projektben levő com.oracle.ojdbc6 dependency-ről:
Ez local maven repóból való, vagyis a projektben található (a memorygame-swarm mappában).
A memorygame-ear (webapp) projektben van, nemcsak a WAR, de a JAR file generálásához is szükséges.

A memorygame-common-entities projekt pom.xml-jében levő com.oracle.ojdbc6 dependency (ami provided), csak az esetleges Hibernate create entities from tables, vagy a HQL editorhoz kell, vagyis a program futtatásához nem.

Először hozzuk létre az MG user/schema-t (jelszó 'mg'), a createMGuser.sql segítségével.
Majd hozzuk létre az MGD user/schema-t (jelszó 'mgd'), a createMGDuser.sql segítségével.
A memorygame-swarm mappában futtasuk az "mvn install" parancsot.
Majd a memorygame-app/memorygame-ear mappába lépve futtassuk az "mvn clean install -P liquibase" parancsot, amivel létrehozzuk a USERS és RESULT táblákat.
Majd a memorygame-app/memorygame-ear mappába lépve futtassuk az "mvn clean install -P liquibase-data" parancsot, amivel létrehozzuk a RESULT_DATA táblát.
/** Alternatív megoldás:
Hozzuk létre a táblákat a usersResultCreate.sql futtatásával (MG user).
Hozzuk létre a ResultData táblát a resultDataCreate.sql futtatásával (MGD user).
Ez pl. SQLDeveloperben egyben lefuttatható,
de pl. az Oracle 10 Xe böngészős adatbáziskezelőjében (apex) a "/" jelekkel elválasztott részeket egyenként kell futtatni.
Utána az alapadatokat a csv (először a users, majd a result) file-okból importáljuk,
vagy a usereket regisztráció útján is létre lehet hozni. **/

Utána a memorygame-ear mappában "mvn thorntail:package".
Ezután a memorygame-ear/target mappából futtatható a java -jar memorygame-ear-thorntail.jar paranccsal.
Ezzel elindítjuk az Ejb modult a localhost 8485 porton.

Utána a memorygame mappában "mvn thorntail:package".
Ezután a memorygame/target mappából futtatható a java -jar memorygame-thorntail.jar paranccsal.
Ez a modul remote EJB hívásokkal éri el az EJB modult és azon keresztül az adatbázist.

Ezután az oldal elérhető a localhost:8484/memorygame url-en.

Wadl file információk: localhost:8484/memorygame/game/application.wadl

Jó játékot!
