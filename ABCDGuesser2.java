import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * This program approximates a value to a relative error less than 1% using De
 * Jagers formula.
 *
 * @author Christopher Miranda
 *
 */
public final class ABCDGuesser2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser2() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        double value = -1;
        while (value <= 0) {
            out.println("Please enter a real positive number.");
            String userNum = in.nextLine();
            if (FormatChecker.canParseDouble(userNum)) {
                value = Double.parseDouble(userNum);
            }
        }
        return value;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in, SimpleWriter out) {
        double value = getPositiveDouble(in, out);
        while (value == 1.0) {
            out.println("Value can't be 1.0, try again.");
            value = getPositiveDouble(in, out);
        }
        return value;

    }

    /**
     * Calculates the approximation given w,x,y,z with their respective
     * exponents a,b,c,d.
     *
     * @param persNums
     *            list of personal numbers
     * @param exponents
     *            list of exponents for each personal number
     *
     * @return the approximation using De Jagers formula given 4 double values
     *         and 4 exponents
     */

    public static double getApprox(double[] persNums, double[] exponents) {
        int i = 0;
        double approx = 1;
        while (i < persNums.length) {
            approx *= Math.pow(persNums[i], exponents[i]);
            i++;
        }
        return approx;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        final double[] exponents = { -5.0, -4.0, -3.0, -2.0, -1.0, -1.0 / 2.0, -1.0 / 3.0,
                -1.0 / 4.0, 0.0, 1.0 / 4.0, 1.0 / 3.0, 1.0 / 2.0, 1.0, 2.0, 3.0, 4.0,
                5.0 };
        final double relativeError = .01;
        final double percent = 100;
        double error = 0;
        double smallerError = percent;

        out.println("What constant would you like to be approximated?");
        double u = getPositiveDouble(in, out);
        out.println("Enter a personal number for w:");
        double w = getPositiveDoubleNotOne(in, out);
        out.println("Enter a personal number for x:");
        double x = getPositiveDoubleNotOne(in, out);
        out.println("Enter a personal number for y:");
        double y = getPositiveDoubleNotOne(in, out);
        out.println("Enter a personal number for z:");
        double z = getPositiveDoubleNotOne(in, out);

        final int numOfVars = 4;
        double[] persNums = { w, x, y, z };
        double[] iterationExp = new double[numOfVars];
        double bestA = 0, bestB = 0, bestC = 0, bestD = 0, bestApprox = 0;

        for (int i = 0; i < exponents.length; i++) {

            for (int j = 0; j < exponents.length; j++) {

                for (int k = 0; k < exponents.length; k++) {

                    for (int l = 0; l < exponents.length; l++) {

                        iterationExp[0] = exponents[i];
                        iterationExp[1] = exponents[j];
                        iterationExp[2] = exponents[k];
                        iterationExp[numOfVars - 1] = exponents[l];
                        double approx = getApprox(persNums, iterationExp);
                        error = Math.abs(u - approx) / u;

                        if (error < smallerError) {
                            smallerError = error;
                            bestA = exponents[i];
                            bestB = exponents[j];
                            bestC = exponents[k];
                            bestD = exponents[l];
                            bestApprox = approx;
                        }

                    }

                }

            }

        }
        out.println("Best exponent a: " + bestA);
        out.println("Best exponent b: " + bestB);
        out.println("Best exponent c: " + bestC);
        out.println("Best exponent d: " + bestD);
        out.println("Best approximation: " + bestApprox);
        out.print("Relative error: ");
        out.print(smallerError * percent, 2, false);
        out.println("%");
        if (smallerError < relativeError) {
            out.println("The relative error is less than 1%!");
        } else {
            out.println("The charming theory has been disproved!");
        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
