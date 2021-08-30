package nextstep.jwp.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import nextstep.jwp.http.ContentType;
import nextstep.jwp.http.FileReader;
import nextstep.jwp.db.InMemoryUserRepository;
import nextstep.jwp.http.HttpError;
import nextstep.jwp.http.HttpRequest;
import nextstep.jwp.http.HttpResponse;
import nextstep.jwp.model.User;

public class RegisterController extends AbstractController {

    private static final String AMPERSAND_DELIMITER = "&";
    private static final String EQUAL_DELIMITER = "=";
    private static final String ACCOUNT = "account";
    private static final String PASSWORD = "password";
    private static final String EMAIL = "email";

    public RegisterController(HttpRequest httpRequest) {
        super(httpRequest);
    }

    @Override
    byte[] get(HttpRequest httpRequest) throws IOException {
        return HttpResponse
                .ok(FileReader.file(httpRequest.uri()), ContentType.findBy(httpRequest.uri()));
    }

    @Override
    byte[] post(HttpRequest httpRequest) throws IOException {
        final String[] body = httpRequest.body().split(AMPERSAND_DELIMITER);
        final Map<String, String> registerInfo = getRequestBody(body);

        final String account = registerInfo.get(ACCOUNT);
        final String password = registerInfo.get(PASSWORD);
        final String email = registerInfo.get(EMAIL);

        final String responseBody = FileReader.file(httpRequest.uri());
        final ContentType contentType = ContentType.findBy(httpRequest.uri());

        if (InMemoryUserRepository.findByAccount(account).isPresent()) {
            return HttpResponse.ok(responseBody, contentType);
        }

        final User user = new User(InMemoryUserRepository.findCurrentId(), account, password,
                email);
        InMemoryUserRepository.save(user);

        return HttpResponse.ok(
                FileReader.file(Controller.INDEX_PAGE),
                ContentType.findBy(Controller.INDEX_PAGE)
        );
    }

    private Map<String, String> getRequestBody(String[] body) {
        final Map<String, String> registerInfo = new LinkedHashMap<>();
        for (String b : body) {
            final String[] split = b.split(EQUAL_DELIMITER);
            registerInfo.put(split[0].trim(), split[1].trim());
        }
        return registerInfo;
    }

    @Override
    byte[] error(HttpError httpError) throws IOException {
        return HttpResponse.error(HttpError.FORBIDDEN);
    }
}
