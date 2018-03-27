package com.epam.internship.carrental.car;

/**
 * Utility class for the car class.
 */
public final class CarUtilities {

    /**
     * Final constructor to prevent instantiation.
     */
    private CarUtilities() {
    }

    /**
     * Updates the car defined as the first parameter, with the parameters of the second car
     * which is the second parameter.
     *
     * @param carToUpdate  updateable car
     * @param newCarParams car with the new parameters
     * @return updated Car object
     */
    public static Car updateCar(final Car carToUpdate, final Car newCarParams) {
        if (newCarParams.getCarType() != null) {
            carToUpdate.setCarType(newCarParams.getCarType());
        }
        if (newCarParams.getFuelUsage() != 0.0) {
            carToUpdate.setFuelUsage(newCarParams.getFuelUsage());
        }
        if (newCarParams.getGearbox() != null) {
            carToUpdate.setGearbox(newCarParams.getGearbox());
        }
        if (newCarParams.getMake() != null) {
            carToUpdate.setMake(newCarParams.getMake());
        }
        if (newCarParams.getModel() != null) {
            carToUpdate.setModel(newCarParams.getModel());
        }
        if (newCarParams.getSeats() != 0) {
            carToUpdate.setSeats(newCarParams.getSeats());
        }
        return carToUpdate;
    }
}
