Guilds
======

Plugin Bukkit pour gérer des guildes


-----------
 Features
-----------

- Créez votre guilde
- Assignez un Q.G. où vos membres pourront se téléporter
- Parlez uniquement aux membres grâce à un canal privé
- Invitez des amis à vous rejoindre ou expulsez un joueur indésirable
- Système d'invitation (accepter/décliner)
- rangs (officier, recrue ...) avec gestion des couleurs dans le canal guilde
- Gestion des joueurs par UUID 
- Gestion des guildes par UUID (permet de les renommer)
- Lister les guildes

------------
  A VENIR
------------

- Protection de votre Q.G. grâce à WorldGuard (ou residence)
- Commande pour renommer sa guilde
- Gestion des couleurs en fonction du rang dans le canal guilde
- Amélioration de l'aide
- Permission en fonction du rang (chef de guilde = toutes, recrue = juste le droit de parler sur le canal)
- expérience de guilde (API pour utiliser par d'autre plugin)
- Lister les membres d'une guilde (avec leur rang)

------------
  BUG CONNUS
------------

- impossible d'avoir 2 guildes avec le même nom

------------
  Commandes et Permissions
------------
- Utilisateurs :

/guilds info [joueur] : affiche les infos du joueur
/guilds invite <joueur> : invite un joueur à rejoindre votre guilde.
/guilds accept/deny : si vous avez une invitation, accepte ou refuse.
/guilds leave : quitte votre guilde.
/base : tp à la taverne de guilde.
/g : parle sur le canal guilde.
/guilds promote <joueur> [rang] : grade le joueur de la guilde.
/guilds demote <joueur> [rang] : dégrade le joueur de la guilde.
/guilds kick <joueur> : vire le joueur de la guilde.

- Administration : 

/guilds create <guild> : create guild.
/guilds remove <guild> : remove guild.
/guilds setbase [guild] : set guilds base.
/guilds add <player> [guild]: add a member (without invitation).
/guilds save: save to file.
/guilds load: load to file.
