
package com.defiLecture.controleur;


/**
 *
 * @author Charles et Joel
 */
public class ActionBuilder {
    public static Action getAction(String actionName) {
        //System.out.print("entrer dans l'action builder");
        if (actionName != null)
            switch (actionName) {
                
                case "afficherPageAccueil":
                    return new AfficherPageMarcheASuivreAction();
                case "afficherPageProfil":
                    return new AfficherPageProfilAction();
                case "afficherPageCodeConduite":
                    return new AfficherPageCodeConduiteAction();
                case "afficherPageGlossaire":
                    return new AfficherPageGlossaireAction();
                case "afficherPageContributeurs":
                    return new AfficherPageContributeursAction();
                case "afficherPageMarcheASuivre":
                    return new AfficherPageMarcheASuivreAction();
                    
      //Inscription
                case "afficherPageInscription":
                    return new AfficherPageInscriptionAction(); 
                case "effectuerInscription":
                    return new EffectuerInscriptionAction();  
      //Connexion
                case "afficherPageConnexion":
                    return new AfficherPageConnexionAction();    
                case "effectuerConnexion":
                    return new EffectuerConnexionAction();
                case "effectuerDeconnexion":
                    return new EffectuerDeconnexionAction();   
                case "effectuerGenerationMotPasse":
                    return new EffectuerGenerationMotPasseAction();
                    
      //Lecture             
                case "afficherPageCreationLecture":
                    return new AfficherPageCreationLectureAction();
                case "afficherPageGestionLecture":
                    return new AfficherPageGestionLectureAction();
                case "effectuerCreationLecture":
                    return new EffectuerCreationLectureAction();
                case "afficherPageModificationLecture":
                    return new AfficherPageModificationLectureAction();
                case "effectuerModificationLecture":
                    return new EffectuerModificationLectureAction();
                case "effectuerSuppressionLecture":
                    return new EffectuerSuppressionLectureAction();
      //Defi
                case "afficherPageCreationDefi":
                    return new AfficherPageCreationDefiAction();
                case "effectuerCreationDefi":
                    return new EffectuerCreationDefiAction();
                case "afficherPageParticipationDefi":
                    return new AfficherPageParticipationDefiAction();
                case "afficherPageInscriptionDefi":
                    return new AfficherPageInscriptionDefiAction();
                case "effectuerInscriptionDefi":
                    return new EffectuerInscriptionDefiAction();
                case "afficherPageModificationDefi":
                    return new AfficherPageModificationDefiAction();
                case "effectuerModificationDefi":
                    return new EffectuerModificationDefiAction();
      //Compte        
                case "afficherPageGestionListeCompte":
                    return new AfficherPageGestionListeComptesAction();
                case "afficherPageAdresseCourriel":
                    return new AfficherPageAdresseCourrielAction();
                case "afficherPageModificationCompte":
                    return new AfficherPageModificationCompteAction();
                case "effectuerModificationCompte":
                    return new EffectuerModificationCompteAction();
                case "effectuerAjoutAvatarCompte":
                    return new EffectuerAjoutAvatarCompteAction();
                case "effectuerSuppressionCompte":
                    return new EffectuerSuppressionCompteAction();
      //Equipe
                case "afficherPageEquipe":
                    return new AfficherPageEquipeAction();
                case "afficherPageCreationEquipe":
                    return new AfficherPageCreationEquipeAction();
                case "effectuerCreationEquipe":
                    return new EffectuerCreationEquipeAction();
                case "afficherPageTableauScores":
                    return new AfficherPageTableauScoresAction();
                case "afficherPageListeEquipes":
                    return new AfficherPageListeEquipesAction();
                case "afficherPageModificationEquipe":
                    return new AfficherPageModificationEquipeAction();
                case "effectuerModificationEquipe":
                    return new EffectuerModificationEquipeAction();
               
      //DemandeEquipe          
                case "effectuerDemandeAdhesionEquipe":
                    return new EffectuerDemandeAdhesionEquipeAction();
                case "afficherPageListeDemandesEquipe":
                    return new AfficherPageListeDemandesEquipeAction();
                case "effectuerAcceptationDemandeAdhesion":
                    return new EffectuerAcceptationDemandeAdhesionAction();
                case "effectuerSuppressionDemandeAdhesion":
                    return new EffectuerSuppressionDemandeAdhesionAction();
                case "effectuerDepartEquipe":
                    return new EffectuerDepartEquipeAction();
                case "effectuerSuspensionMembreEquipe":
                    return new EffectuerSuspensionMembreEquipeAction();
                case "effectuerReaffectationMembreEquipe":
                    return new EffectuerReaffectationMembreEquipeAction();
      //Test Ajax
                case "testAjax":
                    return new ExempleClasseActionAjax();
                    
                default:
                    return new DefaultAction();
                
            }
        
        return new AfficherPageMarcheASuivreAction();
    }
}
