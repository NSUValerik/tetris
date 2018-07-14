package ru.nsu.berdov.tetris.model;

import java.io.*;
import java.util.*;

public final class HighScores
{
    private final ArrayList<String> scores;
    private final String file = "High_scores.txt";
    public final static int CAPACITY = 5;

    public HighScores()
    {
        scores = new ArrayList<String>();
        Comparator<String> comparator = new Comparator<String>()
        {
            public int compare(String o1, String o2)
            {
                StringTokenizer tokenizer = new StringTokenizer(o1);
                String nameO1 = tokenizer.nextToken();
                int valueO1 = Integer.parseInt(tokenizer.nextToken());
                tokenizer = new StringTokenizer(o2);
                String nameO2 = tokenizer.nextToken();
                int valueO2 = Integer.parseInt(tokenizer.nextToken());
                if (valueO1 == valueO2)
                {
                    return nameO1.compareTo(nameO2);
                }
                else if (valueO1 > valueO2)
                {
                    return -1;
                }
                else
                {
                    return 1;
                }
            }
        };
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new FileReader(file));
            for (int i = 0; i < CAPACITY; i++)
            {
                String line = reader.readLine();
                if (null == line || "\n".equals(line) || line.isEmpty())
                {
                    break;
                }
                scores.add(line);
            }
            Collections.sort(scores, comparator);
        }
        catch (FileNotFoundException e)
        {
            System.exit(0);
        }
        catch (IOException e)
        {
            System.exit(0);
        }
        finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            }
            catch (IOException e)
            {
                System.exit(0);
            }
        }
    }

    public void writeFile()
    {
        PrintWriter writer = null;
        try
        {
            writer = new PrintWriter(file);
            for (String score : scores)
            {
                writer.write(score);
            }
        }
        catch (IOException e)
        {
            System.exit(0);
        }
        finally
        {
            if (writer != null)
            {
                writer.close();
            }
        }
    }

    public boolean isSetRecord(int score)
    {
        for (String score1 : scores)
        {
            StringTokenizer tokenizer = new StringTokenizer(score1);
            tokenizer.nextToken();
            int value = Integer.parseInt(tokenizer.nextToken());
            if (score > value)
            {
                return true;
            }
        }
        return scores.size() < CAPACITY;
    }

    public void setRecord(int score, String name)
    {
        if (!"".equals(name))
        {
            boolean flag = false;
            for (int i = 0; i < scores.size(); i++)
            {
                StringTokenizer tokenizer = new StringTokenizer(scores.get(i));
                tokenizer.nextToken();
                int value = Integer.parseInt(tokenizer.nextToken());
                if (score > value)
                {
                    scores.add(i, name + " " + score);
                    flag = true;
                    if(scores.size() > CAPACITY)
                    {
                        scores.remove(CAPACITY);
                    }
                    break;
                }
            }
            if(scores.size() < CAPACITY && !flag)
            {
                scores.add(name + " " + score);
            }
            writeFile();
        }
    }

    public void resetScores()
    {
        scores.clear();
        writeFile();
    }

    public int getScoreSize()
    {
        return scores.size();
    }

    public String getScore(int index)
    {
        return scores.get(index);
    }
}
