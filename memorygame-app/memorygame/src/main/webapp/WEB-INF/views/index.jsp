<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="hu.icell.entities.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>Memory Game</title>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css" />"/>
    <script type="text/javascript">
        window.onload = function () {
            var kepszam = 27;
            var kepek = kepekFeltolt();
            var hatter = "images/hatter1.jpg";
            var darab = 4;
            var elozokartya = null;
            var klikk = 0;
            var hattalVan = darab * darab;
            var datum;
            var resources = "<c:url value="/resources/" />";
            resources = resources.split(";")[0];
            //************************************************************
            // HTMLCollection.prototype.forEach = Array.prototype.forEach;
            //************************************************************

            function getSzam(szam) {
                if (szam < 0) {
                    return "-" + getSzam(-szam);
                }
                if (szam < 10) {
                    return "0" + szam;
                }
                else {
                    return "" + szam;
                }
            }

            function kepekFeltolt() {
                var kepek = [];
                for (var i = 0; i < kepszam; i++) {
                    kepek[i] = getSzam(i) + ".jpg";
                }
                return kepek;
            }

            function getPictures(db) {
                if (db > kepszam) {
                    return null;
                }
                var pictures = [];
                for (var i = 0; i < db; i++) {
                    var van;
                    do {
                        var p = kepek[Math.floor(Math.random() * kepszam)];
                        van = false;
                        for (var j = 0; j < i; j++) {
                            if (pictures[j] === p) {
                                van = true;
                            }
                        }
                        if (!van) {
                            pictures[i] = p;
                        }
                    }
                    while (van);
                }
                return pictures;
            }

            function getKepek(db) {
                var pictures = getPictures(db / 2);
                var kepek2 = [];
                for (var i = 0; i < db / 2; i++) {
                    var van;
                    for (var j = 0; j < 2; j++) {
                        do {
                            van = false;
                            var index = Math.floor(Math.random() * db);
                            if (kepek2[index] !== undefined) {
                                van = true;
                            }
                            if (!van) {
                                kepek2[index] = pictures[i];
                            }
                        }
                        while (van);
                    }
                }
                return kepek2;
            }

            function initializeBackgroundAndBeginGame(db) {
                var content = "";
                var m = 24 / db;
                var wh = (db * 80 + ((6 - db) * 20)) / db - m;
                m /= 2;
                var cardsWh = "" + (db * 80 + ((6 - db) * 20)) + "px";
                kepek2 = getKepek(db * db);
                for (var i = 0; i < (db * db); i++) {
                    var dataImage = "images/" + kepek2[i];
                    var img = '<img src="' + resources + hatter + '" width="100%" height="100%" class="cardImage" data-image="' + dataImage + '" >';
                    content += '<div class="card" style="width:' + wh + 'px; height:' + wh + 'px; margin:' + m + 'px">' + img + '</div>';
                }
                document.getElementById("cards").style.width = cardsWh;
                document.getElementById("cards").style.height = cardsWh;
                document.getElementById("cards").innerHTML = content;
                addClickToCards();
            }

            function initializeBackground(db) {
                var content = "";
                var m = 24 / db;
                var wh = (db * 80 + ((6 - db) * 20)) / db - m;
                m /= 2;
                var cardsWh = "" + (db * 80 + ((6 - db) * 20)) + "px";
                var img = '<img src="' + resources + hatter + '" width="100%" height="100%">';
                for (var i = 0; i < (db * db); i++) {
                    content += '<div class="card" style="width:' + wh + 'px; height:' + wh + 'px; margin:' + m + 'px">' + img + '</div>';
                }
                document.getElementById("cards").style.width = cardsWh;
                document.getElementById("cards").style.height = cardsWh;
                document.getElementById("cards").innerHTML = content;
            }

            document.getElementById("more").onclick = function () {
                if (darab === 6 || megszakit()) {
                    return;
                }
                darab += 2;
                initializeBackground(darab);
                reset();
            };
            document.getElementById("less").onclick = function () {
                if (darab === 2 || megszakit()) {
                    return;
                }
                darab -= 2;
                initializeBackground(darab);
                reset();
            };
            document.getElementById("begin").onclick = function () {
                if (megszakit()) {
                    return;
                }
                initializeBackgroundAndBeginGame(darab);
                reset();
                alert("Játék indul!");
                datum = new Date().getTime();
            };
            function megszakit() {
                return (hattalVan !== darab * darab) && !confirm("Biztos félbe akarod hagyni a játékot?");
            }

//            function addClickToCards() {
//                //***********************************************************************
//                // document.getElementsByClassName("cardImage").forEach(function (item) {
//                // **********************************************************************
//                [].forEach.call(document.getElementsByClassName("cardImage"), function (item) {
//                    item.onclick = function () {
//                        if ((this.getAttribute("src") !== hatter) || (klikk === 1 && this === elozokartya)) {
//                            return;
//                        }
//                        felFordit(this);
//                        klikk = (klikk + 1) & 1;
//                        if (klikk === 1) {
//                            elozokartya = this;
//                        }
//                        else {
//                            if (this.getAttribute("data-image") === elozokartya.getAttribute("data-image")) {
//                                hattalVan -= 2;
//                                if (hattalVan === 0) {
//                                    kesz();
//                                }
//                            }
//                            else {
//                                setTimeout(leFordit, 1000, this);
//                                setTimeout(leFordit, 1000, elozokartya);
//                            }
//                            elozokartya = null;
//                        }
//                    };
//                });
//            }

            function addClickToCards() {
                var cardImages = document.getElementsByClassName("cardImage");
                for (var i = 0; i < cardImages.length; i++) {
                    cardImages[i].onclick = function () {
                        if ((this.getAttribute("src") !== resources + hatter) || (klikk === 1 && this === elozokartya))
                            return;
                        felFordit(this);
                        klikk = (klikk + 1) & 1;
                        if (klikk === 1) {
                            elozokartya = this;
                        } else {
                            if (this.getAttribute("data-image") === elozokartya.getAttribute("data-image")) {
                                hattalVan -= 2;
                                if (hattalVan === 0) {
									setTimeout(kesz, 0);
                                }
                            } else {
                                setTimeout(leFordit, 1000, this);
                                setTimeout(leFordit, 1000, elozokartya);
                            }
                            elozokartya = null;
                        }
                    };
                }
            }

            function felFordit(kartya) {
                kartya.setAttribute("src", resources + kartya.getAttribute("data-image"));
            }

            function leFordit(kartya) {
                kartya.setAttribute("src", resources + hatter);
            }

            function reset() {
                elozokartya = null;
                klikk = 0;
                hattalVan = darab * darab;
            }

            function kesz() {
                var seconds = Math.floor((new Date().getTime() - datum) / 1000);
                // alert("Kész!\nEredmény: " + seconds + " másodperc.");
                reset();
                if (darab == 6) {
                	ment(seconds);
                } else {
                	alert("Kész!\nEredmény: " + seconds + " másodperc.");
                }
            }

            function ment(seconds) {
            	var http = new XMLHttpRequest();
            	var url = "<%= request.getContextPath() %>/game/result/save";
            	var obj = new Object();
            	obj.seconds = seconds;
            	obj.userId = <%= ((User)request.getSession().getAttribute("user")).getId() %>;
            	var params = JSON.stringify(obj);
            	http.open("POST", url, true);

            	//Send the proper header information along with the request
            	http.setRequestHeader("Content-type", "application/json");

            	http.onreadystatechange = function() {//Call a function when the state changes.
            	    if(http.readyState == 4 && http.status == 200) {
            	    	var obj =  JSON.parse(http.responseText);
            	    	var mentes = "Sikerült elmenteni az adatbázisba.";
            	    	if (obj.success.toLowerCase() !== "success") {
                	    	mentes = "Nem sikerült elmenteni az adatbázisba.";
                	    }
            	    	alert("Kész!\nEredmény: " + seconds + " másodperc.\n" + mentes);
            	    	console.log(http.responseText);
            	    } else if (http.readyState == 4) {
            	    	alert("Kész!\nEredmény: " + seconds + " másodperc.");
                	}
            	}
            	http.send(params);
            }

            initializeBackground(darab);
        };
    </script>
</head>
<body>
<div id="body">
	<div id="nav">
		<ul>
			<li><a class="active" href="<%= request.getContextPath() %>">Game</a></li>
			<li><a href="<%= request.getContextPath() %>/game/result">Results</a></li>
			<li><a href="<%= request.getContextPath() %>/game/result/<%= ((User)request.getSession().getAttribute("user")).getId() %>">My results</a></li>
			<li class="right" ><a href="<%= request.getContextPath() %>/game/logout">Logout</a></li>
		</ul>
	</div>
	<div id="main">
	    <h3>Memory Game</h3>
	    <div id="up">
	        <div id="cards">
	
	        </div>
	    </div>
	    <div id="down">
	        <div id="buttons">
	            <input type="button" id="more" value="TÖBB KÁRTYA">
	            <input type="button" id="less" value="KEVESEBB KÁRTYA">
	            <input type="button" id="begin" value="JÁTÉK">
	        </div>
	    </div>
	</div>
</div>
</body>
</html>
