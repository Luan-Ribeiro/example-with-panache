package org.quarkus.test.resource;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.Uni;
import java.net.URI;
import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.quarkus.test.entity.Car;

@Path("/car")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
@ApplicationScoped
public class CarResource {

    @Transactional
    @GET
    public Uni<Car> getByName(@QueryParam("name") String name) {
        return Car.getByName(name);
    }

    @Transactional
    @GET
    @Path(value = "/{id}")
    public Uni<Car> getId(@PathParam("id") Long id) {
        return Car.findById(id);
    }

    @Transactional(TxType.REQUIRED)
    @POST
    public Uni<Response> create(@Valid @RequestBody Car car) {
        return Panache.<Car>withTransaction(car::persistAndFlush).map(created -> Response.created(
            URI.create("/car" + created.getId())).build());
    }

    @Transactional(TxType.REQUIRED)
    @DELETE
    @Path(value = "/{id}")
    public Uni<Long> delete(@PathParam("id") Long id) {
        return Car.deleteById(id);
    }

}
