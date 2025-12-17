# 🚀 GUIDE RAPIDE - Configuration IntelliJ IDEA

## ⚡ Étapes à suivre MAINTENANT

### Étape 1️⃣ : Fermer et Rouvrir le Projet

```
1. Dans IntelliJ IDEA : File → Close Project
2. File → Open
3. Sélectionnez le dossier "TP2 FILM"
4. Cliquez sur OK
```

### Étape 2️⃣ : Importer Maven

Quand IntelliJ ouvre le projet, vous verrez une **notification en haut à droite** :

```
┌─────────────────────────────────────────────┐
│ Maven projects need to be imported          │
│                                              │
│ [Import Changes]  [Enable Auto-Import]      │
└─────────────────────────────────────────────┘
```

**Cliquez sur : "Enable Auto-Import"** ✅

### Étape 3️⃣ : Attendre le Téléchargement

En bas de l'écran, vous verrez :

```
⏳ Downloading: org.openjfx:javafx-controls:21.0.1
⏳ Downloading: org.mongodb:mongodb-driver-sync:4.11.1
...
```

**Attendez 2-3 minutes** que tous les téléchargements se terminent.

### Étape 4️⃣ : Vérifier que ça fonctionne

Ouvrez le fichier `Main.java` :

**AVANT (erreurs en rouge)** ❌
```java
import javafx.application.Application;  // ← en ROUGE
```

**APRÈS (tout en blanc/gris)** ✅
```java
import javafx.application.Application;  // ← reconnu !
```

---

## 🔧 Si la notification Maven n'apparaît PAS

### Solution Alternative :

1. **Ouvrez l'onglet Maven** (à droite de l'écran)
   - Si vous ne le voyez pas : View → Tool Windows → Maven

2. **Cliquez sur l'icône Refresh** ⟳
   ```
   Maven
   ├─ TP2 FILM
   │  ├─ Lifecycle
   │  ├─ Plugins
   │  └─ Dependencies  ← vérifiez ici
   └─ [⟳] ← CLIQUEZ ICI
   ```

3. Maven va télécharger les dépendances

---

## ✅ Checklist de Vérification

Cochez quand c'est fait :

- [ ] Projet rouvert dans IntelliJ
- [ ] Notification Maven "Import Changes" affichée et cliquée
- [ ] Barre de téléchargement apparue en bas
- [ ] Téléchargement terminé (plus de barre de progression)
- [ ] Onglet Maven visible à droite
- [ ] Dossier "Dependencies" dans Maven contient :
  - [ ] org.openjfx:javafx-controls
  - [ ] org.openjfx:javafx-fxml  
  - [ ] org.openjfx:javafx-graphics
  - [ ] org.mongodb:mongodb-driver-sync
  - [ ] org.mongodb:bson
- [ ] Fichier `Main.java` ouvert sans erreurs rouges
- [ ] Fichier `FilmsController.java` ouvert sans erreurs rouges

---

## 🎯 Test Final

**Lancez l'application pour tester :**

```
1. Clic droit sur Main.java
2. Run 'Main.main()'
```

**Si MongoDB n'est pas démarré**, vous verrez une erreur de connexion (normal).  
**Si JavaFX fonctionne**, la fenêtre s'ouvrira ! 🎉

---

## 🆘 Problèmes Courants

### Problème 1 : "Cannot resolve symbol 'javafx'"

**Solution :**
```
File → Invalidate Caches / Restart → Invalidate and Restart
```

### Problème 2 : Maven ne télécharge rien

**Solution :**
```bash
# Dans le terminal IntelliJ (en bas) :
cd "/Users/thomasfriquet/Documents/TP2 FILM"
./setup.sh
```

### Problème 3 : Erreur "java.lang.module.FindException"

**Solution :**
```
File → Project Structure → Project
Vérifier que SDK = 17 (temurin-17)
```

### Problème 4 : Maven n'apparaît pas à droite

**Solution :**
```
View → Tool Windows → Maven
```

---

## 📞 Besoin d'Aide ?

Consultez les fichiers suivants :
- **README.md** - Guide complet
- **IMPORTS_INFO.md** - Liste détaillée des imports
- **pom.xml** - Configuration Maven (ne pas modifier)

---

**C'EST TOUT ! Suivez ces étapes et tout fonctionnera ! 🚀**

