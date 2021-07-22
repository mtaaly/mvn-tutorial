package de.elk.service.rest;

import de.elk.service.model.Benutzer;
import de.elk.service.model.BenutzerDao;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("/benutzer")
public class BenutzerRS {
    @Inject
    private BenutzerDao benutzerDao;

    @GET
    @Path("/list")
    @Produces("application/json")
    public List<Benutzer> getAll(){
        return benutzerDao.getBenutzer();
    }
}
