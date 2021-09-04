package com.example.gestioncontact;

public class Contact {
    String nom,prenom ,numero ;

    public Contact() {
    }

    public Contact(String nom, String prenom, String numero) {
        this.nom = nom;
        this.prenom = prenom;
        this.numero = numero;
    }

    public String getNom() {

        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNumero() {
        return numero;
    }

    public void setNom(String nom) {

        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
    @Override
    public String toString() {
        return "Contact{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", numero='" + numero + '\'' +
                '}';
    }
}
