# 🔧 Instructions de Correction - Erreurs MongoDB

## ✅ Modifications Effectuées

### 1. Fichier `module-info.java` corrigé
J'ai ajouté le module `org.mongodb.driver.core` nécessaire pour accéder aux packages MongoDB :

```java
module tp2.film {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires org.mongodb.driver.core;          // ← AJOUTÉ
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
}
```

### 2. Fichier `pom.xml` mis à jour
J'ai ajouté explicitement la dépendance `mongodb-driver-core` :

```xml
<!-- MongoDB Driver Core -->
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>mongodb-driver-core</artifactId>
    <version>${mongodb.version}</version>
</dependency>
```

## 🚀 ÉTAPES À SUIVRE MAINTENANT DANS INTELLIJ

### ⚠️ IMPORTANT : Recharger Maven

Les fichiers ont été modifiés, mais IntelliJ doit maintenant télécharger et reconnaître les nouvelles dépendances.

### Étape 1️⃣ : Recharger le projet Maven

**Option A : Via la notification**
1. Une notification apparaît en haut à droite : "Maven projects need to be imported"
2. Cliquez sur **"Import Changes"** ou **"Enable Auto-Import"**

**Option B : Via l'onglet Maven**
1. Ouvrez l'onglet **Maven** (à droite de l'écran)
   - Si vous ne le voyez pas : `View` → `Tool Windows` → `Maven`
2. Cliquez sur l'icône **Refresh** ⟳ (en haut de l'onglet Maven)
3. Attendez que Maven télécharge les dépendances

### Étape 2️⃣ : Vérifier le téléchargement

Regardez la barre de progression en bas de l'écran :
```
⏳ Resolving dependencies...
⏳ Downloading: org.mongodb:mongodb-driver-core:4.11.1
```

**Attendez 1-2 minutes** que tous les téléchargements se terminent.

### Étape 3️⃣ : Vérifier que les erreurs ont disparu

Ouvrez le fichier `FilmsController.java` :

**AVANT (erreurs en rouge)** ❌
```java
import static com.mongodb.client.model.Filters.eq;  // ← ROUGE
import com.mongodb.BasicDBObject;                    // ← ROUGE
```

**APRÈS (tout reconnu)** ✅
```java
import static com.mongodb.client.model.Filters.eq;  // ← OK
import com.mongodb.BasicDBObject;                    // ← OK
```

### Étape 4️⃣ : Si les erreurs persistent

**Si après le refresh Maven les erreurs sont toujours là :**

1. **Invalidate Caches** :
   - Menu : `File` → `Invalidate Caches...`
   - Cochez : `Invalidate and Restart`
   - Cliquez sur `Invalidate and Restart`

2. **Rebuild Project** :
   - Menu : `Build` → `Rebuild Project`

3. **Vérifier la structure du module** :
   - Menu : `File` → `Project Structure` → `Modules`
   - Sélectionnez le module `tp2.film`
   - Onglet `Dependencies`
   - Vérifiez que vous voyez :
     - `org.mongodb:mongodb-driver-sync:4.11.1`
     - `org.mongodb:mongodb-driver-core:4.11.1`
     - `org.mongodb:bson:4.11.1`

## 📋 Checklist de Vérification

- [ ] Maven refresh effectué (icône ⟳ cliquée)
- [ ] Téléchargement terminé (pas de barre de progression)
- [ ] Onglet Maven ouvert et dossier "Dependencies" visible
- [ ] Dépendances MongoDB visibles :
  - [ ] mongodb-driver-sync
  - [ ] mongodb-driver-core ← NOUVEAU
  - [ ] bson
- [ ] Fichier `module-info.java` contient `requires org.mongodb.driver.core;`
- [ ] Fichier `FilmsController.java` : imports MongoDB ne sont plus en rouge
- [ ] Fichier `PeopleController.java` : imports MongoDB ne sont plus en rouge

## ✅ Résultat Attendu

Une fois Maven rechargé, **TOUTES** les erreurs suivantes doivent disparaître :

```
❌ Package 'com.mongodb.client.model' is declared in module 'org.mongodb.driver.core', 
   but module 'tp2.film' does not read it

❌ Package 'com.mongodb' is declared in module 'org.mongodb.driver.core', 
   but module 'tp2.film' does not read it

❌ Package 'com.mongodb.client.result' is declared in module 'org.mongodb.driver.core', 
   but module 'tp2.film' does not read it
```

Tous ces packages seront maintenant accessibles ! ✅

## 🎯 Test Final

Pour vérifier que tout fonctionne :

1. Ouvrez `Main.java`
2. Lancez l'application : `Run` → `Run 'Main'`
3. L'application devrait démarrer sans erreurs de compilation

## 📞 Si ça ne fonctionne toujours pas

Vérifiez que :
- Vous utilisez **Java 17** (configuré dans le projet)
- Maven est bien détecté par IntelliJ
- Les fichiers `module-info.java` et `pom.xml` ont bien été modifiés (vérifiez leur contenu)

---

**Temps estimé pour la correction complète : 2-3 minutes**
(principalement le temps de téléchargement des dépendances Maven)

