package ru.berezowsky.nsu.network;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Iterate files in directory.
 */
public class FileList implements Iterable<File>{

    /**
     * Creates FileList.
     *
     * @param address path to directory
     *
     * @throws IOException, FileListException errors with listing files
     */
    public FileList(String address) throws IOException{
        queue = new LinkedList<>();

        File directory = new File(address);
        if (!directory.isDirectory()){
            throw new FileListException(address + " (is not a directory)");
        }

        queue.addAll(listFiles(directory));
    }

    private ArrayList<File> listFiles(File directory){
        File[] files = directory.listFiles();
        if (files == null){
            return new ArrayList<>();
        } else {
            return new ArrayList<>(Arrays.asList(files));
        }
    }

    public File next(){
        File file = null;

        while (!queue.isEmpty()) {
            for (ListIterator iter = queue.listIterator(); iter.hasNext(); ) {
                file = (File) iter.next();
                iter.remove();

                if (Files.isSymbolicLink(Paths.get(file.getAbsolutePath()))) {
                    continue;
                } else if (file.isFile()) {
                    return file;
                } else if (file.isDirectory()) {
                    listFiles(file).forEach(iter::add);
                    file = null;
                }
            }
        }


        return file;
    }

    @Override
    public Iterator iterator(){
        return new Iterator();
    }

    private class Iterator implements java.util.Iterator<File>{
        @Override
        public File next(){
            return queue.poll();
        }

        @Override
        public boolean hasNext(){
            if (queue.isEmpty()){
                return false;
            }

            File file = queue.getFirst();

            if (Files.isSymbolicLink(Paths.get(file.getAbsolutePath()))) {
                queue.removeFirst();
                return hasNext();
            } else if (file.isFile()) {
                return true;
            } else if (file.isDirectory()) {
                queue.addAll(listFiles(file));
                queue.removeFirst();
                return hasNext();
            }

            throw new NoSuchElementException();
        }
    }

    private LinkedList<File> queue;
}