# DemoTheatre
Application de démonstration de jQuery-Seat-Charts

Cette application montre l'utilisation du plugin jQuery-Seat-Charts (https://github.com/mateuszmarkowski/jQuery-Seat-Charts) pour gérer la 
sélection et l'achat de place de théatre en cliquant sur une carte de la salle.

Pour pouvoir tester l'application, il vous faut créer une base de données réprésentant (de manière extrêmement simplifiée) un théatre,
les spectacles qu'il accueille et les places vendues pour ceux-ci.

Pour vous aider à créer cette base sur la BD Oracle vous disposez d'un script de creation des tables `createTables` situé dans le répertoire 
`src/sql`.

1. dans l'onglet service de netbeans ouvrir une connexion à la base Oracle (attention, éviter d'uiliser la base de votre groupe
projet pour que les tables créées n'interfèrent pas avec les tables de votre projet).
1. ouvrir le fichier `createTables.sql` et choisir la connexion ouverte à l'étape précédente?
1. exécuter ce fichier.


