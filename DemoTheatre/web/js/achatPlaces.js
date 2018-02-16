/* 
 * Copyright (C) 2018 genoud
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
var firstSeatLabel = 1;
var sc;
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
                $("#achatBtn").prop("disabled", false);
                return 'selected';
            } else if (this.status() == 'selected') {
                // la place est désélectionnée
                var nbPlaceSelectees = sc.find('selected').length - 1;
                $nbPlaces.text(nbPlaceSelectees);
                if (nbPlaceSelectees === 0) {
                    $("#achatBtn").prop("disabled", true);
                    $prixTotal.text(0);
                } else {
                    $prixTotal.text(calculerPrixTotal(sc) - this.data().price);
                }
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
                var nbPlaceSelectees = sc.find('selected').length;
                $('#nbplaces').text(nbPlaceSelectees);
                if (nbPlaceSelectees === 0) {
                    $("#achatBtn").prop("disabled", true);
                    $prixTotal.text(0);
                } else {
                    $("#achatBtn").prop("disabled", false);
                    $prixTotal.text(calculerPrixTotal(sc));
                }
            }
        });
    }
});

function majPanier(sc) {
    var nbPlaceSelectees = sc.find('selected').length;
    $('#nbplaces').text(nbPlaceSelectees);
    if (nbPlaceSelectees === 0) {
        $("#achatBtn").prop("disabled", true);
        $prixTotal.text(0);
    } else {
        $("#achatBtn").prop("disabled", false);
        $prixTotal.text(calculerPrixTotal(sc));
    }
}


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


