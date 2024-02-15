package netology.ru.diplom.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import netology.ru.diplom.entities.File;
import netology.ru.diplom.repositories.FileRepository;
import netology.ru.diplom.security.JWTUtil;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StorageService {
    private final FileRepository fileRepository;
    private final JWTUtil jwtTokenUtils;

    public StorageService(FileRepository fileRepository, JWTUtil jwtTokenUtils) {
        this.fileRepository = fileRepository;
        this.jwtTokenUtils = jwtTokenUtils;
    }

    public List<FileResponse> getFiles(String authToken, int limit) {
        String owner = jwtTokenUtils.getUsernameFromToken(authToken.substring(7));
        Optional<List<File>> fileList = fileRepository.findAllByOwner(owner);
        return fileList.get().stream().map(fr -> new FileResponse(fr.getFilename(), fr.getSize()))
                .limit(limit)
                .collect(Collectors.toList());
    }

    public void uploadFile(String authToken, File file) throws IOException {
        String owner = jwtTokenUtils.getUsernameFromToken(authToken.substring(7));
        fileRepository.save(new File(file.getFilename(), file.getType(), file.getSize(), file.getContent(), owner));
    }

    public void deleteFile(String authToken, String filename) {
        String owner = jwtTokenUtils.getUsernameFromToken(authToken.substring(7));
        fileRepository.removeByFilenameAndOwner(filename, owner);
    }

    public File downloadFile(String authToken, String filename) {
        String owner = jwtTokenUtils.getUsernameFromToken(authToken.substring(7));
        return fileRepository.findByFilenameAndOwner(filename, owner);
    }

    public void renameFile(String authToken, String filename, String newFilename) {
        String owner = jwtTokenUtils.getUsernameFromToken(authToken.substring(7));
        fileRepository.renameFile(filename, newFilename, owner);
    }
}
