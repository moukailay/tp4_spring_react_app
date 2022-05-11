package com.tp3.service;


import com.tp3.execeptions.ClientNotFoundException;
import com.tp3.execeptions.DocumentNotFoundException;
import com.tp3.model.*;
import com.tp3.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EmployeService {

    private ClientRepository clientRepository;
    private LivreRepository livreRepository;
    private DvdRepository dvdRepository;
    private CdRepository cdRepository;
    private PretDocumentRepository pretDocumentRepository;
    private DocumentRepository documentRepository;


    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    public EmployeService ( ClientRepository clientRepository, LivreRepository livreRepository, DvdRepository dvdRepository, CdRepository cdRepository, AmendeRepository amendeRepository, PretDocumentRepository pretDocumentRepository, DocumentRepository documentRepository) {
        this.clientRepository = clientRepository;
        this.livreRepository = livreRepository;
        this.dvdRepository = dvdRepository;
        this.cdRepository = cdRepository;
        this.pretDocumentRepository = pretDocumentRepository;
        this.documentRepository = documentRepository;
    }

    // client

    public List<Livre> listeLivre() {
        return livreRepository.findAll();
    }
    public List<Livre> listeLivreDisponible() {
        return livreRepository.findLivreDisponible(1);
    }
    public List<Dvd> listeDvd() {return dvdRepository.findAll();}
    public List<Dvd> listeDvdDisponible() {
        return dvdRepository.findDvdDisponible(1);
    }
    public List<Cd> listeCd() {
        return cdRepository.findAll();
    }
    public List<Cd> listeCdDisponible() {
        return cdRepository.findCdDisponible(1);
    }

    public List<Document> listeDocument() {
        return documentRepository.findAll();
    }

    public Livre enregistrerLivre(String titre, String auteur, String editeur, int nbExemplaire, Date datePub, String genre, int dureeMaxpret, int nbPages) {
        Livre livre = Livre.builder()
                .titre(titre)
                .auteur(auteur)
                .editeur(editeur)
                .datePub(datePub)
                .genre(genre)
                .nbExemplaire(nbExemplaire)
                .dureeMaxPret(dureeMaxpret)
                .nbPages(nbPages)
                .build();
        return livreRepository.save(livre);
    }

    public Cd enregistrerCd(String titre, String auteur, String editeur, int nbExemplaire, Date datePub, String genre, int dureeMaxpret, int duree) {
        Cd cd = Cd.builder()
                .titre(titre)
                .auteur(auteur)
                .editeur(editeur)
                .datePub(datePub)
                .genre(genre)
                .nbExemplaire(nbExemplaire)
                .dureeMaxPret(dureeMaxpret)
                .duree(duree)
                .build();
       return cdRepository.save(cd);
    }

    public Dvd enregistrerDvd(String titre, String auteur, String editeur, int nbExemplaire, Date datePub, String genre, int dureeMaxpret, int duree) {
        Dvd dvd = Dvd.builder()
                .titre(titre)
                .auteur(auteur)
                .editeur(editeur)
                .datePub(datePub)
                .genre(genre)
                .nbExemplaire(nbExemplaire)
                .dureeMaxPret(dureeMaxpret)
                .duree(duree)
                .build();
        return dvdRepository.save(dvd);
    }

    public Client enregistrerClient(String nom, String prenom, String adresse) {
        Client client = Client.builder()
                .nom(nom)
                .prenom(prenom)
                .adresse(adresse)
                .build();
       return clientRepository.save(client);
    }

    public List<Client> listeClient() {
        return clientRepository.findAll();
    }


    public Client mettreAJourClient(Client client) {
        return clientRepository.save(client);
    }

    public Client getClient(long id) throws ClientNotFoundException {

        Optional<Client> client = clientRepository.findById(id);
        if (client.isPresent()) {
            return client.get();
        }
        throw new ClientNotFoundException("Pas de client enregistré avec l'ID" + id);
    }

    public void supprimerClient(Long id) throws ClientNotFoundException {
        Long count = clientRepository.countByidUser(id);
        if (count == null || count == 0) {
            throw new ClientNotFoundException("Pas de client enregistré avec l'ID" + id);
        }
        clientRepository.deleteById(id);
    }

    //Livre
    public void enregistrerDocument(Document document) {
        documentRepository.save(document);
    }

    public List<Document> findByTitreDocument(String titre) throws DocumentNotFoundException {
        List<Document> doc = documentRepository.findByTitreContains(titre);
        if (doc.isEmpty()) {
            throw new DocumentNotFoundException("Pas de document");
        }
        return doc;
    }


    public List<Document> findByCategorieDocument(String genre) throws DocumentNotFoundException {
        List<Document> doc = documentRepository.findByGenre(genre);
        if (doc.isEmpty()) {
            throw new DocumentNotFoundException("Pas de document");
        }
        return doc;
    }

    public List<Document> findByAnneeDocument(int annee) throws DocumentNotFoundException {
        List<Document> doc = documentRepository.findByAnnePub(annee);
        if (doc.isEmpty()) {
            throw new DocumentNotFoundException("Pas de document");
        }
        return doc;
    }

    public List<Document> findByAuteurDocument(String auteur) throws DocumentNotFoundException {
        List<Document> doc = documentRepository.findByAuteur(auteur);
        if (doc.isEmpty()) {
            throw new DocumentNotFoundException("Pas de document");
        }
        return doc;
    }

    public void findAllPretDocumentClient(Client client) throws DocumentNotFoundException {
        List<PretDocument> pretDoc = pretDocumentRepository.findAll();
        if (pretDoc.isEmpty()) {
            throw new DocumentNotFoundException("Pas de document");
        }
        System.out.println("Liste des livres empruntés par le client " + client.getNom() + " " + client.getPrenom());
        System.out.println("TITRE             AUTEUR            DATE RETOUR");
        for (PretDocument pret : pretDoc) {
            if (pret.getClient().getIdUser() == client.getIdUser()) {
                System.out.println(pret.getDocument().getTitre() + "             " + pret.getDocument().getAuteur() + "         " + simpleDateFormat.format(pret.getDateRetour()));
            }
        }

    }

    public List<PretDocument> findAllPretDocumentEnCours() throws DocumentNotFoundException {
        List<PretDocument> pretDoc = pretDocumentRepository.findByStatutRetour("non retourné");

        return pretDoc;
    }

    public List<PretDocument> findAllPretLivreEnCours() throws DocumentNotFoundException {
        List<PretDocument> pretDoc = pretDocumentRepository.findByStatutRetour("non retourné");
        List <Livre> listeLivre=livreRepository.findAll();
        List<PretDocument> pretDoc1=new ArrayList<>();
        for (PretDocument pret : pretDoc) {
            for (Livre li : listeLivre) {
                if (pret.getDocument().getIdDoc() == li.getIdDoc()) {
                    pretDoc1.add(pret);
                }
            }

        }
        return pretDoc1;
    }

    public List<PretDocument> findAllPretDvdEnCours() throws DocumentNotFoundException {
        List<PretDocument> pretDoc = pretDocumentRepository.findByStatutRetour("non retourné");
        List <Dvd> listeDvd=dvdRepository.findAll();
        List<PretDocument> pretDoc1=new ArrayList<>();
        for (PretDocument pret : pretDoc) {
            for (Dvd d : listeDvd) {
                if (pret.getDocument().getIdDoc() == d.getIdDoc()) {
                    pretDoc1.add(pret);
                }
            }

        }
        return pretDoc1;
    }

    public List<PretDocument> findAllPretCdEnCours() throws DocumentNotFoundException {
        List<PretDocument> pretDoc = pretDocumentRepository.findByStatutRetour("non retourné");
        List <Cd> listeCd=cdRepository.findAll();
        List<PretDocument> pretDoc1=new ArrayList<>();
        for (PretDocument pret : pretDoc) {
            for (Cd cd : listeCd) {
                if (pret.getDocument().getIdDoc() == cd.getIdDoc()) {
                    pretDoc1.add(pret);
                }
            }

        }
        return pretDoc1;
    }

    public void enregistrerPret(Date datePret, Client client, Document document, String statutRetour) throws ParseException {
        if (document.getNbExemplaire() > 0) {
            PretDocument pret = PretDocument.builder()
                    .datePret(datePret)
                    .dateRetour(CalculerDateRetour(document.getDureeMaxPret()))
                    .client(client)
                    .document(document)
                    .statutRetour(statutRetour)
                    .build();
            pretDocumentRepository.save(pret);
            document.setNbExemplaire(document.getNbExemplaire() - 1);
            documentRepository.save(document);
        } else {

            System.out.println("le Document " + document.getTitre() + " n'est pas disponible");

        }
    }

    public PretDocument enregistrerPretDoc(Date datePret, Client client, Document document, String statutRetour) throws ParseException {

            PretDocument pret = PretDocument.builder()
                    .datePret(datePret)
                    .dateRetour(CalculerDateRetour(document.getDureeMaxPret()))
                    .client(client)
                    .document(document)
                    .statutRetour(statutRetour)
                    .build();
            pretDocumentRepository.save(pret);
            document.setNbExemplaire(document.getNbExemplaire() - 1);
            documentRepository.save(document);
            return pret;
    }

    public void supprimerPret(Long id) throws ClientNotFoundException {
        int count = pretDocumentRepository.countByidPret(id);
        if (count == 0) {
            throw new ClientNotFoundException("Pas d'emprunt enregistré avec l'ID" + id);
        }
        pretDocumentRepository.deleteById(id);
    }

    public static Date CalculerDateRetour(int nombreJour) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, nombreJour);

        return simpleDateFormat.parse(simpleDateFormat.format(cal.getTime()));
    }

    public Document getDocument(long id) {

        Optional<Document> doc = documentRepository.findById(id);

        return doc.get();
    }
    public Livre getLivre(long id) {
        Optional<Livre> livre = livreRepository.findById(id);
        return livre.get();
    }


    public Dvd getDvd(long id) {

        Optional<Dvd> dvd = dvdRepository.findById(id);

        return dvd.get();
    }

    public Cd getCd(long id) {

        Optional<Cd> cd = cdRepository.findById(id);

        return cd.get();
    }

    public PretDocument getPretDocument(long id) {

        Optional<PretDocument> pretDoc = pretDocumentRepository.findById(id);

        return pretDoc.get();
    }
    public Livre mettreAJourLivre(Livre livre) {
        return livreRepository.save(livre);
    }

    public Dvd mettreAJourDvd(Dvd dvd) {

        return dvdRepository.save(dvd);
    }
    public Cd mettreAJourCd(Cd cd) {

        return cdRepository.save(cd);
    }
    public void supprimerLivre(Long id) throws DocumentNotFoundException {
        Long count = livreRepository.countByidDoc(id);
        if (count == null || count == 0) {
            throw new DocumentNotFoundException("Pas de document enregistré avec l'ID" + id);
        }
        livreRepository.deleteById(id);
    }

    public void supprimerDvd(Long id) throws DocumentNotFoundException {
        Long count = dvdRepository.countByidDoc(id);
        if (count == null || count == 0) {
            throw new DocumentNotFoundException("Pas de document enregistré avec l'ID" + id);
        }
        dvdRepository.deleteById(id);
    }

    public void supprimerCd(Long id) throws DocumentNotFoundException {
        Long count = cdRepository.countByidDoc(id);
        if (count == null || count == 0) {
            throw new DocumentNotFoundException("Pas de document enregistré avec l'ID" + id);
        }
        cdRepository.deleteById(id);
    }
}


