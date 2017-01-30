package service;

import api.Country;
import api.Service;
import api.State;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Class description
 *
 * @author lobseer on 29.01.2017.
 */

public class ServiceImpl implements Service {
    public static final Logger loger = Logger.getLogger(ServiceImpl.class);
    private static final double CROWDED = 50d;

    private List<Country> world;

    public ServiceImpl() {
    }

    public ServiceImpl(List<Country> world) {
        this.world = world;
    }

    Country getCountry(String name) {
        if (name == null) throw new NullPointerException("Country name is null");
        return world.stream().filter((p) -> p.getName().equals(name)).findAny().orElseThrow(NoSuchElementException::new);
    }

    @Override
    public Long getCountryPopulation(String name) {
        Country temp = getCountry(name);
        return temp == null ? null : temp.getPopulation();
    }

    @Override
    public List<Country> getOvercrowdedCountry() {
        return world.stream().filter((p)->(p.getPopulation()/p.getSquare().doubleValue())>CROWDED).collect(Collectors.toList());
    }

    @Override
    public BigDecimal getCountrySquare(String name) {
        return getCountry(name).getSquare();
    }

    @Override
    public BigDecimal getTotalDensityOfPeople(String name) {
        Country temp = getCountry(name);
        return new BigDecimal(temp.getPopulation()/temp.getSquare().doubleValue());
    }

    @Override
    public BigDecimal getAverageDensityOfPeopleByStates(String name) {
        Country temp = getCountry(name);
        return new BigDecimal((temp.getPopulation()/temp.getSquare().doubleValue())/temp.getStates().size());
    }

    @Override
    public List<State> getOvercrowdedStates(String name) {
        Country temp = getCountry(name);
        return temp.getStates().stream().filter((p)->(p.getPopulation()/p.getSquare().doubleValue())>CROWDED).collect(Collectors.toList());
    }

    @Override
    public State getBiggestState(String name) {
        Country temp = getCountry(name);
        return temp.getStates().stream().max((p1,p2)->p1.getSquare().max(p2.getSquare())==p1?1:-1).get();
    }
}
