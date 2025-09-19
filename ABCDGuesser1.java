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
public final class ABCDGuesser1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser1() {
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
            value = getPositiveDouble(in, out);
            out.println("Value can't be 1.0, try again.");
        }
        return value;

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
        out.println("Emter a personal number for w:");
        double w = getPositiveDoubleNotOne(in, out);
        out.println("Emter a personal number for x:");
        double x = getPositiveDoubleNotOne(in, out);
        out.println("Emter a personal number for y:");
        double y = getPositiveDoubleNotOne(in, out);
        out.println("Emter a personal number for z:");
        double z = getPositiveDoubleNotOne(in, out);

        double bestA = 0, bestB = 0, bestC = 0, bestD = 0, bestApprox = 0;

        int i = 0;
        while (i < exponents.length) {
            int j = 0;
            while (j < exponents.length) {
                int k = 0;
                while (k < exponents.length) {
                    int l = 0;
                    while (l < exponents.length) {
                        double approx = Math.pow(w, exponents[i])
                                * Math.pow(x, exponents[j]) * Math.pow(y, exponents[k])
                                * Math.pow(z, exponents[l]);
                        error = Math.abs(u - approx) / u;
                        if (error < smallerError) {
                            smallerError = error;
                            bestA = exponents[i];
                            bestB = exponents[j];
                            bestC = exponents[k];
                            bestD = exponents[l];
                            bestApprox = Math.pow(w, bestA) * Math.pow(x, bestB)
                                    * Math.pow(y, bestC) * Math.pow(z, bestD);

                        }
                        l++;
                    }
                    k++;
                }
                j++;
            }
            i++;
        }
        out.println("Best exponent a: " + bestA);
        out.println("Best exponent b: " + bestB);
        out.println("Best exponent c: " + bestC);
        out.println("Best exponent d: " + bestD);
        out.println("Best approximation: " + bestApprox);
        out.print("Relative error: ");
        out.print(smallerError * percent, 2, false);
        out.println(" %");
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
