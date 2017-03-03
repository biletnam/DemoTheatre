# DemoTheatre
Application de démonstration de jQuery-Seat-Charts

Cette application montre l'utilisation du plugin jQuery-Seat-Charts (https://github.com/mateuszmarkowski/jQuery-Seat-Charts) pour gérer la 
sélection et l'achat de place de théatre en cliquant sur une carte de la salle.

Pour pouvoir tester l'application, il vous faut créer une base de données réprésentant (de manière extrêmement simplifiée) un théatre,
les spectacles qu'il accueille et les places vendues pour ceux-ci.

Pour vous aider à créer cette base sur la BD Oracle vous disposez d'un script de creation des tables `createTables.sql` situé dans le répertoire 
`src/sql`.

1. dans l'onglet service de netbeans ouvrir une connexion à la base Oracle (**attention**, évitez d'utiliser la base de votre groupe
projet pour que les tables créées n'interfèrent pas avec les tables de votre projet).
1. ouvrir le fichier `createTables.sql` et choisir la connexion ouverte à l'étape précédente?
1. exécuter ce fichier.


Il faut ensuite remplir la table des places. Pour cela il existe un programme d'initalisation (`PlacesInitialiser` situé dans le package  `im2ag.m2pcci.theatre.util` défini dans les Test Packages) qui utilise une carte de la salle définie 
dans un fichier texte (`carte.txt`) situé dans le répertoire `test\data`. Pour exécuter ce programme :

1. Modifier le fichier `jdbc.properties` situé dans le package `im2ag.m2pcci.theatre.util` afin de mettre votre login et mot de passe BD
1. Exécuter le programme `PlacesInitialiser`
1. Verifier que les 620 places définies dans la carte sont bien dans la table `PLACES_VENDUES`

Ensuite créez une ou plusieurs entrées dans la table `LESSPECTACLES`

Vous pouvez maintenant lancer l'application web `DemoTheatre`, mais auparavant pensez bien à modifier le fichier de
configuration `context.xml` pour y indiquer votre login et mot de passe de connexion BD.

**Attention:** il se peut que lorsque vous faites un Clean-Build sur l'application la commande échoue. Dans ce cas là placez dans l'onglet services et arrêtez le serveur tomcat. La commmande Clean-Build devrait alors fonctionner.



