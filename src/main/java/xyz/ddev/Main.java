package xyz.ddev;

import java.io.*;
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        String path = "ip_addresses";
        int size = 2147483647;
        BitSet firstBitSet = new BitSet(size); // first bit is 0
        BitSet secondBitSet = new BitSet(size); // first bit is 1

        int counter = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(path)))
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] octets = line.split("\\.");
                byte first = (byte) Integer.parseInt(octets[0]);
                byte second = (byte) Integer.parseInt(octets[1]);
                byte third = (byte) Integer.parseInt(octets[2]);
                byte forth = (byte) Integer.parseInt(octets[3]);

                int IP = ((first & 0xFF) << 24) | ((second & 0xFF) << 16) | ((third & 0xFF) << 8) | (forth & 0xFF);

                if ((first >> 7 & 1) == 0)
                {
                    if (firstBitSet.get(IP))
                    {
                        continue;
                    }
                    counter++;
                    firstBitSet.set(IP, true);
                }
                else
                {
                    if (secondBitSet.get(Math.abs(IP)))
                    {
                        continue;
                    }
                    counter++;
                    secondBitSet.set(Math.abs(IP), true);
                }
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        finally
        {
            System.out.println("Unique IPv4 addresses count:" + counter);
        }
    }
}