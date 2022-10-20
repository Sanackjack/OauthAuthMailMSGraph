package com.example.mdauthmail.Service;

import com.azure.identity.ClientSecretCredential;
import com.example.mdauthmail.config.AppConfigPath;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.models.BodyType;
import com.microsoft.graph.models.EmailAddress;
import com.microsoft.graph.models.ItemBody;
import com.microsoft.graph.models.Message;
import com.microsoft.graph.models.Recipient;
import com.microsoft.graph.models.UserSendMailParameterSet;
import com.microsoft.graph.requests.GraphServiceClient;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;

@Service
@Slf4j
public class EmailService {

    private final ClientSecretCredential clientSecretCredential;
    private final AppConfigPath appConfigPath;

    @Autowired
    public EmailService(ClientSecretCredential clientSecretCredential, AppConfigPath appConfigPath) {
        this.clientSecretCredential = clientSecretCredential;
        this.appConfigPath = appConfigPath;
    }

    public void sendEmailTest() {
     //***** Example Send Mail HTML TYPE
        StringBuilder builder = new StringBuilder();
        builder.append("<div>")
                .append("<p>Dear All,</p>")
                .append("<p>Hello world.</p>")
                .append("</div><div>");

        sendMail("Test Subject OAuth", builder.toString());
    }

    private void sendMail(String subject, String content) {

        final TokenCredentialAuthProvider tokenCredentialAuthProvider =
                new TokenCredentialAuthProvider(
                        Collections.singletonList(appConfigPath.getMailScope())
                        , clientSecretCredential);

        GraphServiceClient<Request> graphClient = GraphServiceClient.builder().authenticationProvider(tokenCredentialAuthProvider).buildClient();
        Message message = new Message();
        message.subject = subject;
        ItemBody body = new ItemBody();
        body.contentType = BodyType.HTML;
        body.content = content;
        message.body = body;
        message.toRecipients = createRecipients();
        boolean saveToSentItems = false;
        graphClient.users(appConfigPath.getMailSender())
                .sendMail(UserSendMailParameterSet
                        .newBuilder()
                        .withMessage(message)
                        .withSaveToSentItems(saveToSentItems)
                        .build())
                .buildRequest()
                .post();

    }

    private LinkedList<Recipient> createRecipients() {
        LinkedList<Recipient> toRecipientsList = new LinkedList<Recipient>();
        for (String recipient : appConfigPath.getMailReceipt()) {
            Recipient toRecipients = new Recipient();
            EmailAddress emailAddress = new EmailAddress();
            emailAddress.address = recipient;
            toRecipients.emailAddress = emailAddress;
            toRecipientsList.add(toRecipients);
        }
        return toRecipientsList;
    }
}
