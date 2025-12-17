#!/bin/bash

echo "======================================"
echo "  TP2 FILM - Configuration Maven"
echo "======================================"
echo ""
echo "Pour configurer les dépendances du projet, suivez ces étapes:"
echo ""
echo "1. Ouvrez IntelliJ IDEA"
echo "2. File → Open → Sélectionnez ce dossier"
echo "3. IntelliJ détectera le fichier pom.xml"
echo "4. Cliquez sur 'Load Maven Project' dans la notification en haut à droite"
echo "5. Attendez que Maven télécharge les dépendances (JavaFX + MongoDB)"
echo ""
echo "OU utilisez le panneau Maven:"
echo "1. Cliquez sur l'onglet 'Maven' à droite"
echo "2. Cliquez sur l'icône de refresh (⟳) pour recharger le projet"
echo ""
echo "Dépendances qui seront installées:"
echo "  - JavaFX 21.0.1 (controls, fxml, graphics)"
echo "  - MongoDB Driver 4.11.1 (sync, bson)"
echo ""
echo "======================================"
echo ""

# Vérifier si Maven est installé
if command -v mvn &> /dev/null; then
    echo "✅ Maven est installé: $(mvn -version | head -n 1)"
    echo ""
    read -p "Voulez-vous télécharger les dépendances maintenant? (o/n) " -n 1 -r
    echo ""
    if [[ $REPLY =~ ^[OoYy]$ ]]; then
        echo "Téléchargement des dépendances..."
        mvn dependency:resolve
        echo ""
        echo "✅ Dépendances téléchargées!"
        echo "Vous pouvez maintenant ouvrir le projet dans IntelliJ IDEA."
    fi
else
    echo "⚠️  Maven n'est pas installé sur votre système."
    echo "IntelliJ IDEA utilisera son Maven intégré."
    echo ""
    echo "Pour installer Maven manuellement:"
    echo "  brew install maven"
fi

