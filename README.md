# memorygame-swarm

A program 8-as Javával futtatható.

Adatbázis:
Oracle 10 XE
Először hozzuk létre a táblákat a usersResultCreate.sql futtatásával.
Ez pl. SQLDeveloperben egyben lefuttatható,
de pl. az Oracle 10 Xe böngészős adatbáziskezelőjében a "/" jelekkel elválasztott részeket egyenként kell futtatni.
Utána az alapadatokat a csv (először a users, majd a result) file-okból importáljuk,
vagy a usereket regisztráció útján is létre lehet hozni.

A projektben levő com.oracle.ojdbc6 dependenciről:
Ez privát nexus repóból való, ha ilyened nincsen, akkor a memorygame-common-entities (ebben csak a Hibernate-es entity generálás táblából miatt van)
és a memorygame projektek pom.xml-jeiből ki kell venni az ojdbc6 dependecy-ket és helyette ezekbe a projektekbe kell egy-egy ojdbc6.jar-t importálni.

A memorygame-swarm mappában mvn clean install.
Utána a memorygame mappában mvn wildfly-swarm:package.
Ezután a memorygame/target mappából futtatható a java -jar memorygame-swarm.jar paranccsal.

Ezután az oldal elérhető a localhost:8080/memorygame url-en.

Jó játékot!
