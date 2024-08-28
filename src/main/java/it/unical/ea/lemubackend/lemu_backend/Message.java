package it.unical.ea.lemubackend.lemu_backend;

public class Message {
    public String recovery_password(String name, String password) {
        String passwordMessage;
        String changePasswordReminder = "";

        if (password.isEmpty()) {
            passwordMessage = "Poiché il tuo account è stato autenticato tramite un servizio esterno (Google, Facebook, ecc.), non è stata generata una password. Per accedere al tuo account, utilizza il servizio di autenticazione esterno che hai scelto.";
        } else {
            passwordMessage = "Password temporanea: " + password;
            changePasswordReminder = "\n\nTi ricordiamo che è fondamentale mantenere riservata questa informazione e di modificarla al più presto per garantire la sicurezza del tuo account. Puoi cambiare la tua password accedendo alla sezione \"profilo\" dall'applicazione una volta effettuato l'accesso.";
        }

        return String.format(
                """
                        Caro/a %s,

                        Abbiamo ricevuto una richiesta per il recupero della password associata al tuo account Lemu. %s%s

                        Se non hai richiesto questo recupero della password, ti preghiamo di contattarci immediatamente per proteggere il tuo account.

                        Grazie per la tua attenzione e collaborazione.

                        Cordiali saluti,

                        Il team di Lemu
                        [LemuServizioClienti@gmail.com]
                        """,
                name,
                passwordMessage,
                changePasswordReminder
        );
    }

    public String registrationConfirmation(String nomeUtente) {
        return String.format(
                """
                        Caro/a %s,

                        Grazie per esserti registrato/a a Lemu! Siamo felici di darti il benvenuto nella nostra community e-commerce.

                        Ti incoraggiamo a esplorare il nostro sito e a scoprire tutte le fantastiche offerte e prodotti che abbiamo selezionato per te. Se hai bisogno di assistenza o hai domande, il nostro team di supporto è sempre disponibile ad aiutarti.

                        Buon shopping e grazie per aver scelto Lemu!

                        Cordiali saluti,

                        Il team di Lemu
                        [LemuServizioClienti@gmail.com]
                        """,
                nomeUtente
        );
    }

    public String passwordChangeSuccess(String nomeUtente) {
        return String.format(
                """
                        Caro/a %s,

                        La tua password è stata modificata con successo. Da ora in poi, utilizza la nuova password per accedere al tuo account Lemu.

                        Ti consigliamo di mantenere questa password in un luogo sicuro e di non condividerla con nessuno per garantire la sicurezza del tuo account.

                        Se non sei stato tu a modificare la password, ti preghiamo di contattarci immediatamente per proteggere il tuo account.

                        Grazie per la tua attenzione e collaborazione.

                        Cordiali saluti,

                        Il team di Lemu
                        [LemuServizioClienti@gmail.com]
                        """,
                nomeUtente
        );
    }
}
