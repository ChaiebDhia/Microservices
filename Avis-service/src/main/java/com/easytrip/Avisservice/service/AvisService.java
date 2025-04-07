package com.easytrip.Avisservice.service;


import com.easytrip.Avisservice.Repository.AvisRepository;
import com.easytrip.Avisservice.Repository.ReactionAvisRepository;
import com.easytrip.Avisservice.models.Avis;
import com.easytrip.Avisservice.models.ReactionAvis;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.easytrip.Avisservice.UserClient.UserClient;
import feign.FeignException;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvisService implements IAvisService {

    private final AvisRepository avisRepository;
    private final UserClient userClient;
    private final ReactionAvisRepository reactionAvisRepository;
    @Override
    public Avis createAvis(Avis avis) {
        try {
            // Vérifier que l'utilisateur existe via le microservice user-service
            userClient.getUserById(avis.getUtilisateurId());
        } catch (FeignException.NotFound e) {
            throw new RuntimeException("Utilisateur introuvable avec l'id: " + avis.getUtilisateurId());
        } catch (FeignException.Unauthorized e) {
            throw new RuntimeException("Non autorisé à accéder à l'utilisateur.");
        } catch (FeignException e) {
            throw new RuntimeException("Erreur lors de la communication avec le service utilisateur.");
        }

        avis.setDateAvis(LocalDateTime.now());
        avis.setApprouve(false); // par défaut, l'avis n'est pas approuvé
        return avisRepository.save(avis);
    }

    @Override
    public Avis getAvisById(Long id) {
        return avisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Avis non trouvé avec l'id: " + id));
    }

    @Override
    public List<Avis> getAllAvis() {
        return avisRepository.findAll();
    }

    @Override
    public List<Avis> getAvisByVoyageId(Long voyageId) {
        return avisRepository.findByVoyageId(voyageId);
    }

    @Override
    public List<Avis> getAvisByUtilisateurId(Long utilisateurId) {
        return avisRepository.findByUtilisateurId(utilisateurId);
    }

    @Override
    public Avis updateAvis(Long id, Avis updatedAvis) {
        Avis existingAvis = getAvisById(id);
        existingAvis.setNote(updatedAvis.getNote());
        existingAvis.setCommentaire(updatedAvis.getCommentaire());
        existingAvis.setApprouve(updatedAvis.isApprouve());
        return avisRepository.save(existingAvis);
    }

    @Override
    public void deleteAvis(Long id) {
        avisRepository.deleteById(id);
    }

    public double calculerScorePondere(Avis avis) {
        double base = avis.getNote();

        // Pondérer par ancienneté (moins d'impact si plus vieux)
        long joursDepuis = ChronoUnit.DAYS.between(avis.getDateAvis(), LocalDateTime.now());
        double facteurTemps = 1.0 / (1 + (joursDepuis / 30.0)); // diminue tous les 30 jours

        // Pondération si approuvé
        double facteurApprouve = avis.isApprouve() ? 1.2 : 0.8;

        return base * facteurTemps * facteurApprouve;
    }

    public List<Avis> getAvisParPertinence(List<Avis> avisList) {
        return avisList.stream()
                .sorted((a1, a2) -> Double.compare(calculerScorePondere(a2), calculerScorePondere(a1)))
                .collect(Collectors.toList());
    }


    // Méthode pour approuver ou refuser un avis
    public Avis modererAvis(Long avisId, boolean approuve) {
        Avis avis = avisRepository.findById(avisId)
                .orElseThrow(() -> new RuntimeException("Avis non trouvé"));

        avis.setApprouve(approuve);  // Met à jour l'état "approuve" de l'avis
        return avisRepository.save(avis);  // Sauvegarde l'avis mis à jour
    }

    public ReactionAvis ajouterReaction(Long avisId, Long userId, boolean liked) {
        Avis avis = avisRepository.findById(avisId)
                .orElseThrow(() -> new RuntimeException("Avis non trouvé"));

        // Vérifier si l'utilisateur a déjà réagi
        if (reactionAvisRepository.existsByAvisIdAndUserId(avisId, userId)) {
            throw new RuntimeException("L'utilisateur a déjà réagi à cet avis");
        }

        ReactionAvis reaction = ReactionAvis.builder()
                .avis(avis)
                .userId(userId)
                .liked(liked)
                .build();

        return reactionAvisRepository.save(reaction);
    }

    public List<ReactionAvis> getReactionsByAvis(Long avisId) {
        return reactionAvisRepository.findByAvisId(avisId);
    }
}
