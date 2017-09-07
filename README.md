# memorygame-swarm

Rendszerfeltételek:

A program 8-as Javával futtatható.
Maven: 3.3.9 vagy 3.5.0

Adatbázis:
Oracle 10 XE (de 11-es adatbázis is megteszi)

Először hozzuk létre az MG user/schema-t (jelszó 'mg'), a createMGuser.sql segítségével.
Majd a mamorygame-app/memorygame mappába lépve futtassuk a mvn clean install -P liquibase parancsot, amivel létrehozzuk a USERS és RESULT táblákat
/** Alternatív megoldás:
Hozzuk létre a táblákat a usersResultCreate.sql futtatásával.
Ez pl. SQLDeveloperben egyben lefuttatható,
de pl. az Oracle 10 Xe böngészős adatbáziskezelőjében (apex) a "/" jelekkel elválasztott részeket egyenként kell futtatni.
Utána az alapadatokat a csv (először a users, majd a result) file-okból importáljuk,
vagy a usereket regisztráció útján is létre lehet hozni. **/

A projektben levő com.oracle.ojdbc6 dependenciről:
Ez privát nexus repóból való, ha ilyened nincsen, akkor a memorygame-common-entities (ebben csak a Hibernate-es entity generálás táblából miatt van)
és a memorygame projektek pom.xml-jeiből ki kell venni az ojdbc6 dependecy-ket és helyette ezekbe a projektekbe kell egy-egy ojdbc6.jar-t importálni.

A memorygame-swarm mappában mvn clean install.
Utána a memorygame mappában mvn wildfly-swarm:package.
Ezután a memorygame/target mappából futtatható a java -jar memorygame-swarm.jar paranccsal.

Ezután az oldal elérhető a localhost:8484/memorygame url-en.

Jó játékot!
