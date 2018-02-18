# DemoEnvoiMail

Ce projet NetBeans contient une application web de démonstration de génération d'un fichier pdf et de l'envoi de celui-ci par email.

* Cette application utilise l'API standard JavaMail et son [implémentation de référence](https://java.net/projects/javamail/pages/Home) pour l'envoi des courriels. 
* Pour la génération de fichier pdf, la librairie open source [Apache PDFBox](https://pdfbox.apache.org/index.html) version 2.0.4 est utilisée. 
* Pour la génération d'un QRCode, la librairie [zxing core 3.3.0](https://github.com/zxing/zxing) est utilisée.

Dans l'idéal, la configuration du serveur de mail sortant (SMTP) utilisé par cette application devrait être effectuée dans le fichier `context.xml`. 
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Context antiJARLocking="true" path="/DemoWebMail">
    <Resource 
        name="mail/DEMO"
        type="javax.mail.Session"
        auth="Container" 
        mail.smtp.auth="true"
        mail.smtp.starttls.enable="true"
        mail.smtp.host="nomDuServeurSMTP"
        mail.smtp.port="587"
        mail.smtp.user="votreIdentifiantsurLeServeurDeMail"
        mail.smtp.password="votreMotDePasseSurLeServeurDeMail"
        password="votreMotDePasseSurLeServeurDeMail"
    />
</Context>
```
Mais pour des raisons liées à l'environnement de travail de l'UFR IM2AG, ce n'est pas le cas ici (pour pouvoir utiliser une telle configuration, il faudrait que vous disposiez de droits administrateur pour pouvoir recopier le `.jar` dans le repertoire `lib` du serveur Apache Tomcat). Pour contourner ce problème et permettre à l'application de fonctionner sur les machines de l'UFR IM2AG, 
la configuration STMP est définie dans le fichier de déploiment `web.xml` sous la forme de paramètres d'initialisation de la servlet envoyant le mail (servlet `TicketCtrler`).
```xml
        ...
    <!--
       Configuration de la servlet TicketCtrler
    -->
    <servlet>
        <servlet-name>TicketSenderCtrler</servlet-name>
        <servlet-class>im2ag.m2pcci.maildemo.servlets.TicketsCtrler</servlet-class>
        <!--
           Paramètres d'initialisation de la servlet. Ils sont récupérés à 
           l'initalisation de la servlet, lors de son chargement par le container
           (Tomcat).
        -->
        <!--
           Paramètres d'initialisation de la servlet. Ils sont récupérés à 
           l'initalisation de la servlet, lors de son chargement par le container
           (Tomcat).
        -->
        <init-param>
            <!--    
                serveur smtp  
            -->
            <param-name>smtp_server</param-name>
            <param-value>A_Completer</param-value>
        </init-param>
        <init-param>
            <!--    
                port serveur smtp  
            -->
            <param-name>smtp_port</param-name>
            <param-value>A_Completer</param-value>
        </init-param>
        <init-param>
            <!--    
                nom utilisateur mail 
            -->
            <param-name>mail_user_name</param-name>
            <param-value>*A_Completer*</param-value>
        </init-param>
        <init-param>
            <!--    
                mot de passe utilisateur mail  
            -->
            <param-name>mail_user_passwd</param-name>
            <param-value>A_Completer</param-value>
        </init-param>
        <init-param>
            <!--    
                adresse mail pour l'expéditeur 
            -->
            <param-name>sender</param-name>
            <param-value>A_Completer</param-value>
        </init-param>
        <init-param>
            <!--
               le titre (objet) du mail
            -->
            <param-name>title</param-name>
            <param-value>Votre ticket électronique   !!!</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <!--
           définition de l'URL pour cette servlet
        -->
        <servlet-name>TicketSenderCtrler</servlet-name>
        <url-pattern>/sendTicket</url-pattern>
    </servlet-mapping>
        ...
 ```
Pour envoyer un email depuis les machine de l'UFR I2MAG vous allez utiliser le serveur smtp de l'UGA. Mais le serveur SMTP
 (`smtps.univ-grenoble-alpes.fr`) n'est pas directement accessible depuis les machines du réseau enseignement de l'UFR-IM2AG. 
Sur celles-ci vous allez passer par une machine tierce qui elle dispose des autorisations nécessaires et redirigera vos envois de mail sur
le serveur `smtps.univ-grenoble-alpes.fr`.

Les paramètres de configuration sont donc:

* serveur smtp: **152.77.82.189**     -- redirige vers smtps.univ-grenoble-alpes.fr
* port du serveur: **587**
* nom utilisateur : **votre login agalan**
* mot de passe : **votre mot de passe agalan**  **--ATTENTION** de ne pas le déposer sur Github !!

##**ATTENTION:** 

* Cette configuration ne marche que pour les machines en salles de TP de l'UFR IM2AG, mais pas lorsque vous êtes sur wifi-campus où bien sur le VPN-UGA. 
* Par ailleurs, lorsque le fichier `web.xml` contient, comme ici , un élément `servlet-mapping`, celui-ci prend le dessus sur une annotation `@WebServlet(name = "nomServlet", urlPatterns = {"/uneURL"})` qui serait définie dans le code de la classe Java de la Servlet. Pour éviter tout problème, ne mettez pas d'annotation `@WebServlet` dans votre servlet, et mettez bien le même nom pour les  éléments `<servlet>` et `<servlet-mapping>`

```xml
    <servlet>
        <servlet-name>TicketSenderCtrler</servlet-name>
        <servlet-class>im2ag.m2pcci.maildemo.servlets.TicketsCtrler</servlet-class>
	...
    </servlet>
    <servlet-mapping>
        <servlet-name>TicketSenderCtrler</servlet-name>
        <url-pattern>/sendTicket</url-pattern>
    </servlet-mapping>
```

Lorsque vous n'êtes pas sur les machines de l'UFR-IM2AG, pour envoyer des mails depuis votre ordinateur vous pouvez utiliser le serveur de mail `smtps.univ-grenoble-alpes.fr`(qui remplace le pramètre `smtp: 152.77.82.189`) ou le serveur mail de votre messagerie personnelle. Ainsi si vous avez un compte gmail, les paramètres de configuration seront les suivants:

* serveur smtp: **smtp.gmail.fr**
* port du serveur: **587**
* nom utilisateur : **votre identifiant gmail**   (adresse mail gmail)
* mot de passe : **votre mot de passe gmail**  **--ATTENTION** de ne pas le déposer sur Github !!

Cependant, par défaut, google bloque l'accès au serveur smtp pour des application tierces telles notre programme java (il utilise une [validation en deux étapes](https://www.google.fr/intl/fr/landing/2step/#tab=how-it-works). Ainsi si vous essayez d'envoyer un mail, vous aurez l'exception suivante:

```
javax.mail.AuthenticationFailedException: 534-5.7.14 <https://accounts.google.com/signin/continue?sarp=1&scc=1&plt=AKgnsbt3
534-5.7.14 O1mX8ROss9z4eKiPOewyTMOGF39ZIfgttjQGszEsWaZESJvEB-vpf-phyGFy1ixD0_75SD
534-5.7.14 Xl-jaqElq83fuNIa7j9n0ZZXAr1MtALJo8ZirkH0H6Fesx2APePg9m_UbdQBjs4zOumaJ_
534-5.7.14 r8spjquXLp1ls2fcyN2RRIFh95nQZZ9nifZ6qhjcdkJ0ZMW80tHWRjxz_IWf6HfC17XMVl
534-5.7.14 wXXd-KT9D-Uy_zxlT0A5IvnBwbhdk> Please log in via your web browser and
534-5.7.14 then try again.
534-5.7.14  Learn more at
534 5.7.14  https://support.google.com/mail/answer/78754 l41sm5522114wre.23 - gsmtp

	at com.sun.mail.smtp.SMTPTransport$Authenticator.authenticate(SMTPTransport.java:914)
	at com.sun.mail.smtp.SMTPTransport.authenticate(SMTPTransport.java:825)
	at com.sun.mail.smtp.SMTPTransport.protocolConnect(SMTPTransport.java:730)
	at javax.mail.Service.connect(Service.java:388)
	at javax.mail.Service.connect(Service.java:246)
	at javax.mail.Service.connect(Service.java:195)
	at javax.mail.Transport.send0(Transport.java:254)
	at javax.mail.Transport.send(Transport.java:124)
```

Pour résoudre ce problème vous pouvez désactiver ce mécanisme de sécurité, en vous connectant à https://www.google.com/settings/security/lesssecureapps (Pour en savoir plus à ce sujet: [Autoriser les applications moins sécurisées à accéder à votre compte gmail] (https://support.google.com/accounts/answer/6010255?hl=fr). 


