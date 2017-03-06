<%-- 
    Document   : achatPlaces
    Created on : 2 mars 2017, 20:25:34
    Author     : Philippe GENOUD - Université Grenoble Alpes - Lab LIG-Steamer
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>JSC Demo</title>
        <meta charset="UTF-8">
        <link href='http://fonts.googleapis.com/css?family=Lato:400,700' rel='stylesheet' type='text/css'>
        <link href="js/jQuery-Seat-Charts/jquery.seat-charts.css" rel="stylesheet" type="text/css"/>
        <link href="css/styleTheatre.css" rel="stylesheet" type="text/css"/>
    </head>

    <body>
        <div class="wrapper">
            <h1>
                Spectacle ${spectacle.titre}
            </h1>
            <div id="map-container">
                <div id="seat-map">
                    <div class="front-indicator">Scène</div>
                </div>
                <div id="commande">
                    <div id="legend"></div>
                    <h3>Votre sélection</h3>
                    <p>Nbre de places: <strong><span id="nbplaces">0</span></strong></p>
                    <p>Prix Total: <strong><span id="prixtotal">0</span> €</strong></p>
                    <button id="achatBtn">Acheter</button>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
        <script src="js/jQuery-Seat-Charts/jquery.seat-charts.min.js" type="text/javascript"></script>
        <script>
            var firstSeatLabel = 1;
            $(document).ready(function () {
                var $detailCategorie = $('#detail-categories'),
                        $nbPlaces = $('#nbplaces'),
                        $prixTotal = $('#prixtotal'),
                        sc = $('#seat-map').seatCharts({
                    map: [
                        '__AAAAAAAAAAAAAA__AAAAAAAAAAAAAA__',
                        '__AAAAAAAAAAAAAA__AAAAAAAAAAAAAA__',
                        '__AAAAAAAAAAAAAA__AAAAAAAAAAAAAA__',
                        '__AAAAAAAAAAAAAA__AAAAAAAAAAAAAA__',
                        '__AAAAAAAAAAAAAA__AAAAAAAAAAAAAA__',
                        '__________________________________',
                        '_BBBBBBBBBBBBBBB__BBBBBBBBBBBBBBB_',
                        '_BBBBBBBBBBBBBBB__BBBBBBBBBBBBBBB_',
                        '_BBBBBBBBBBBBBBB__BBBBBBBBBBBBBBB_',
                        '_BBBBBBBBBBBBBBB__BBBBBBBBBBBBBBB_',
                        '_BBBBBBBBBBBBBBB__BBBBBBBBBBBBBBB_',
                        '_BBBBBBBBBBBBBBB__BBBBBBBBBBBBBBB_',
                        '_BBBBBBBBBBBBBBB__BBBBBBBBBBBBBBB_',
                        '_BBBBBBBBBBBBBBB__BBBBBBBBBBBBBBB_',
                        '_BBBBBBBBBBBBBBB__BBBBBBBBBBBBBBB_',
                        '_BBBBBBBBBBBBBBB__BBBBBBBBBBBBBBB_',
                        '_CCCCCCCCCCCCCCC__CCCCCCCCCCCCCCC_',
                        '_CCCCCCCCCCCCCCC__CCCCCCCCCCCCCCC_',
                        '_CCCCCCCCCCCCCCC__CCCCCCCCCCCCCCC_',
                        '_CCCCCCCCCCCCCCC__CCCCCCCCCCCCCCC_',
                        '_CCCCCCCCCCCCCCC__CCCCCCCCCCCCCCC_',
                        '_CCCCCCCCCCCCCCC__CCCCCCCCCCCCCCC_'
                    ],
                    seats: {
                        A: {
                            price: 100,
                            classes: 'categorieA', // votre classe CSS spécifique
                            category: 'A'
                        },
                        B: {
                            price: 40,
                            classes: 'categorieB', // votre classe CSS spécifique
                            category: 'B'
                        },
                        C: {
                            price: 20,
                            classes: 'categorieC', // votre classe CSS spécifique
                            category: 'C'
                        }
                    },
                    naming: {
                        top: false,
                        getLabel: function (character, row, column) {
                            return firstSeatLabel++;
                        },
                    },
                    legend: {
                        node: $('#legend'),
                        items: [
                            ['A', 'available', 'Catégorie A'],
                            ['B', 'available', 'Catégorie B'],
                            ['C', 'available', 'Catégorie C'],
                            [, 'unavailable', 'Place non disponible']
                        ]
                    },
                    click: function () {
                        if (this.status() === 'available') {
                            /*
                             * Une place disponible a été sélectionnée
                             * Mise à jour du nombre de places et du prix total
                             *
                             * Attention la fonction .find  ne prend pas en compte 
                             * la place qui vient d'être selectionnée, car la liste des
                             * places sléectionnées ne sera modifiée qu'après le retour de cette fonction.
                             * C'est pourquoi il est nécessaire d'ajouter 1 au nombre de places et le prix
                             * de la place sélectionnée au prix calculé.
                             */
                            $nbPlaces.text(sc.find('selected').length + 1);
                            $prixTotal.text(calculerPrixTotal(sc) + this.data().price);
                            return 'selected';
                        } else if (this.status() == 'selected') {
                            $nbPlaces.text(sc.find('selected').length - 1);
                            $prixTotal.text(calculerPrixTotal(sc) - this.data().price);
                            // la place est désélectionnée
                            return 'available';
                        } else if (this.status() == 'unavailable') {
                            // la place a déjà été achetée.
                            return 'unavailable';
                        } else {
                            return this.style();
                        }
                    }
                });
                majPlanSalle();

                setInterval(majPlanSalle, 10000); //every 10 seconds


                $("#achatBtn").click(function () {
                    acheter(sc);
                });

                function majPlanSalle() {
                    $.ajax({
                        type: 'get',
                        url: 'placesNonDisponibles',
                        dataType: 'json',
                        success: function (reponse) {
                            // iteration sur toutes les réservations de reponse
                            $.each(reponse.bookings, function (index, reservation) {
                                //mettre le status du siège correspondant à non disponible
                                sc.status(reservation.rang + '_' + reservation.colonne, 'unavailable');
                            });
                            $nbPlaces.text(sc.find('selected').length);
                            $prixTotal.text(calculerPrixTotal(sc));
                        }
                    });
                }
            });



            function calculerPrixTotal(sc) {
                var total = 0;
                //retrouver toutes les places sélectionnées et sommer leur prix
                sc.find('selected').each(function () {
                    total += this.data().price;
                });
                return total;
            }

            function acheter(sc) {
                var params = "";
                var premier = true;
                sc.find('selected').each(function () {
                    if (premier) {
                        params = params + "place=";
                        premier = false;
                    } else {
                        params = params + "&place=";
                    }
                    params = params + this.settings.label;
                });
                location.replace("acheterPlaces?" + params);
            }
        </script>
    </body>
