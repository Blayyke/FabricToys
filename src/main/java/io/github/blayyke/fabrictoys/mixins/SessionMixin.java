package io.github.blayyke.fabrictoys.mixins;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.body.RequestBodyEntity;
import net.minecraft.client.util.Session;
import org.json.JSONObject;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Session.class)
public class SessionMixin {
    @Mutable
    @Shadow
    @Final
    private String username;

    @Mutable
    @Shadow
    @Final
    private String accessToken;

    @Mutable
    @Shadow
    @Final
    private Session.AccountType accountType;

    @Mutable
    @Shadow
    @Final
    private String uuid;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void init(CallbackInfo info) throws Exception {
        JSONObject auth = authenticate();
        JSONObject profile = auth.getJSONObject("selectedProfile");

        this.username = profile.getString("name");
        this.uuid = profile.getString("id");

        this.accessToken = auth.getString("accessToken");
        this.accountType = Session.AccountType.MOJANG;
    }

    private JSONObject authenticate() throws Exception {
        JSONObject payload = new JSONObject();
        payload.put("agent",
                new JSONObject().put("name", "Minecraft").put("version", 1)
        );
        payload.put("username", "blakeredenius@gmail.com");
        payload.put("password", "RedeniuS.18");        // FIXME dont push this

        return postReadURL(payload);
    }

    private JSONObject postReadURL(JSONObject payload) throws Exception {
        Unirest.setTimeouts(15000, 15000);
        RequestBodyEntity post = Unirest.post("https://authserver.mojang.com/authenticate")
                .header("Content-Type", "application/json")
                .body(payload);
        return post.asJson().getBody().getObject();
    }
}