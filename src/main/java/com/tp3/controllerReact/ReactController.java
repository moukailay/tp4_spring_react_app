package com.tp3.controllerReact;

import com.tp3.execeptions.ClientNotFoundException;
import com.tp3.execeptions.DocumentNotFoundException;
import com.tp3.model.*;
import com.tp3.service.EmployeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(originPatterns = "*")
public class ReactController {
    private EmployeService employeService;


    @Autowired
    public ReactController ( EmployeService employeService ) {
        this.employeService = employeService;
    }

    @CrossOrigin("http//localhost:3001")
    @GetMapping("/client")
    public List<Client> afficherListeClient () {
        return employeService.listeClient();
    }

    @CrossOrigin("http//localhost:3001")
    @PostMapping("/client")
    public Client createClient ( @RequestBody Client client ) {
        return employeService.enregistrerClient( client.getNom() , client.getPrenom() , client.getAdresse() );
    }


    @GetMapping("/client/{id}")
    public ResponseEntity<Client> getClientById ( @PathVariable long id )
            throws ClientNotFoundException {
        Client client = employeService.getClient( id );

        return ResponseEntity.ok( client );
    }


    @PutMapping("/client/{id}")
    public ResponseEntity<Client> updateClient ( @PathVariable long id ,
                                                 @RequestBody Client client )
            throws ClientNotFoundException {
        Client updateClient = employeService.getClient( id );

        updateClient.setNom( client.getNom() );
        updateClient.setPrenom( client.getPrenom() );
        updateClient.setAdresse( client.getAdresse() );

        employeService.mettreAJourClient( updateClient );

        return ResponseEntity.ok( updateClient );
    }

    // build delete CLIENT REST API


    @DeleteMapping("/client/{id}")
    public ResponseEntity<HttpStatus> deleteClient ( @PathVariable long id )
            throws ClientNotFoundException {
        employeService.supprimerClient( id );
        return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }

    @GetMapping("/livre")
    public List<Livre> afficherListeLivre () {
        return employeService.listeLivre();

    }

    @PostMapping("/livre")
    public Livre createLivre ( @RequestBody Livre livre ) {
        return employeService.enregistrerLivre( livre.getTitre() ,
                livre.getAuteur() ,
                livre.getEditeur() ,
                livre.getNbExemplaire() ,
                livre.getDatePub() ,
                livre.getGenre() ,
                livre.getDureeMaxPret() ,
                livre.getNbPages() );
    }



    @GetMapping("/livre/{id}")
    public ResponseEntity<Livre> getLivreById ( @PathVariable long id ) {
        Livre livre = employeService.getLivre( id );

        return ResponseEntity.ok( livre );
    }



    @PutMapping("/livre/{id}")
    public ResponseEntity<Livre> updateLivre ( @PathVariable long id , @RequestBody Livre livre ) throws DocumentNotFoundException {
        Livre updateLivre = employeService.getLivre( id );


        updateLivre.setTitre( livre.getTitre() );
        updateLivre.setAuteur( livre.getAuteur() );
        updateLivre.setEditeur( livre.getEditeur() );
        updateLivre.setDatePub( livre.getDatePub() );
        updateLivre.setGenre( livre.getGenre() );
        updateLivre.setDureeMaxPret( livre.getDureeMaxPret() );
        updateLivre.setNbPages( livre.getNbPages() );
        updateLivre.setNbExemplaire( livre.getNbExemplaire() );

        employeService.mettreAJourLivre( updateLivre );

        return ResponseEntity.ok( updateLivre );
    }

    // build delete Livre REST API


    @DeleteMapping("/livre/{id}")
    public ResponseEntity<HttpStatus> deleteLivre ( @PathVariable long id ) throws DocumentNotFoundException {
        employeService.supprimerLivre( id );
        return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }


    @GetMapping("/dvd")
    public List<Dvd> afficherListeDvd () {
        return employeService.listeDvd();
    }



    @PostMapping("/dvd")
    public Dvd createDvd ( @RequestBody Dvd dvd ) {
        return employeService.enregistrerDvd( dvd.getTitre() ,
                dvd.getAuteur() ,
                dvd.getEditeur() ,
                dvd.getNbExemplaire() ,
                dvd.getDatePub() ,
                dvd.getGenre() ,
                dvd.getDureeMaxPret() ,
                dvd.getDuree() );
    }

    // build get Dvd by id REST API


    @GetMapping("/dvd/{id}")
    public ResponseEntity<Dvd> getDvdById ( @PathVariable long id ) {
        Dvd dvd = employeService.getDvd( id );
        return ResponseEntity.ok( dvd );
    }

    // build update dvd REST API


    @PutMapping("/dvd/{id}")
    public ResponseEntity<Dvd> updateDvd ( @PathVariable long id , @RequestBody Dvd dvd ) {
        Dvd updateDvd = employeService.getDvd( id );


        updateDvd.setTitre( dvd.getTitre() );
        updateDvd.setAuteur( dvd.getAuteur() );
        updateDvd.setEditeur( dvd.getEditeur() );
        updateDvd.setDatePub( dvd.getDatePub() );
        updateDvd.setGenre( dvd.getGenre() );
        updateDvd.setDureeMaxPret( dvd.getDureeMaxPret() );
        updateDvd.setDuree( dvd.getDuree() );
        updateDvd.setNbExemplaire( dvd.getNbExemplaire() );

        employeService.mettreAJourDvd( updateDvd );

        return ResponseEntity.ok( updateDvd );
    }

    // build delete dvd REST API


    @DeleteMapping("/dvd/{id}")
    public ResponseEntity<HttpStatus> deleteDvd ( @PathVariable long id ) throws DocumentNotFoundException {
        employeService.supprimerDvd( id );
        return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }



    @GetMapping("/cd")
    public List<Cd> afficherListeCd () {
        return employeService.listeCd();
    }


    @PostMapping("/cd")
    public Cd createCd ( @RequestBody Cd cd ) {
        return employeService.enregistrerCd( cd.getTitre() ,
                cd.getAuteur() ,
                cd.getEditeur() ,
                cd.getNbExemplaire() ,
                cd.getDatePub() ,
                cd.getGenre() ,
                cd.getDureeMaxPret() ,
                cd.getDuree() );
    }

    // build get cd by id REST API



    @GetMapping("/cd/{id}")
    public ResponseEntity<Cd> getCdById ( @PathVariable long id ) throws DocumentNotFoundException {
        Cd cd = employeService.getCd( id );
        return ResponseEntity.ok( cd );
    }

    // build update cd REST API


    @PutMapping("/cd/{id}")
    public ResponseEntity<Cd> updateCd ( @PathVariable long id , @RequestBody Cd cd ) throws DocumentNotFoundException {
        Cd updateCd = employeService.getCd( id );


        updateCd.setTitre( cd.getTitre() );
        updateCd.setAuteur( cd.getAuteur() );
        updateCd.setEditeur( cd.getEditeur() );
        updateCd.setDatePub( cd.getDatePub() );
        updateCd.setGenre( cd.getGenre() );
        updateCd.setDureeMaxPret( cd.getDureeMaxPret() );
        updateCd.setDuree( cd.getDuree() );
        updateCd.setNbExemplaire( cd.getNbExemplaire() );

        employeService.mettreAJourCd( updateCd );

        return ResponseEntity.ok( updateCd );
    }

    // build delete cd REST API


    @DeleteMapping("/cd/{id}")
    public ResponseEntity<HttpStatus> deleteCd ( @PathVariable long id ) throws DocumentNotFoundException {
        employeService.supprimerCd( id );
        return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }



    @GetMapping("/empruntLivre")
    public List<PretDocument> afficherListeEmpruntLivre () throws DocumentNotFoundException {
        return employeService.findAllPretLivreEnCours();
    }


    @PostMapping("/saveEmprunt/{clientId}/{livreId}")
    private PretDocument EnregistrerPret ( @PathVariable long clientId , @PathVariable long livreId )
            throws ClientNotFoundException, ParseException, ParseException {

        Document doc = employeService.getDocument( livreId );
        if ( doc.getNbExemplaire() > 0 ) {
            return employeService.enregistrerPretDoc( new Date() , employeService.getClient( (clientId) ) ,
                    employeService.getDocument( (livreId) ) , "non retourné" );
        } else {
            return null;
        }
    }


    @GetMapping("/livreDispo")
    public List<Livre> afficherListeLivreDispo () throws DocumentNotFoundException {
        return employeService.listeLivreDisponible();
    }


    @DeleteMapping("/supEmprunt/{id}")
    public ResponseEntity<HttpStatus> supprimerEmprunt ( @PathVariable("id") Long id )
            throws ClientNotFoundException {

        PretDocument pretDoc = employeService.getPretDocument( id );
        Document doc = pretDoc.getDocument();
        doc.setNbExemplaire( doc.getNbExemplaire() + 1 );
        employeService.enregistrerDocument( doc );
        employeService.supprimerPret( id );
        return new ResponseEntity<>( HttpStatus.NO_CONTENT );
    }
    //dvd

    @GetMapping("/empruntDvd")
    public List<PretDocument> afficherListeEmpruntDvd ()
            throws DocumentNotFoundException {
        return employeService.findAllPretDvdEnCours();
    }



    @GetMapping("/dvdDispo")
    public List<Dvd> afficherListeDvdDispo () {
        return employeService.listeDvdDisponible();
    }

    // CD


    @GetMapping("/empruntCd")
    public List<PretDocument> afficherListeEmpruntCd ()
            throws DocumentNotFoundException {
        return employeService.findAllPretCdEnCours();
    }


    @GetMapping("/cdDispo")
    public List<Cd> afficherListeCdDispo () {
        return employeService.listeCdDisponible();
    }

}
