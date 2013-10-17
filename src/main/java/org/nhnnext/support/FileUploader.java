package org.nhnnext.support;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

public class FileUploader {
	private static final String ATTACHMENT_ROOT_DIR = "/Users/younkue/Documents/workspace_ee/nextweb/webapp/images";

	// private static final String ATTACHMENT_ROOT_DIR =
	// "D:\\next-workspace\\jee-workspace\\next-board\\webapp\\images";

	public static String upload(MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) {
			return null;
		}
		transferToAttachmentDir(multipartFile);
		return multipartFile.getOriginalFilename();
	}

	private static File transferToAttachmentDir(MultipartFile multipartFile) {
		File destFile = getDestinationFile(multipartFile.getOriginalFilename());
		try {
			multipartFile.transferTo(destFile);
		} catch (Exception ex) {
			throw new IllegalArgumentException(destFile + "Move Error");
		}
		return destFile;
	}

	public static File getDestinationFile(String fileName) {
		return new File(ATTACHMENT_ROOT_DIR + File.separator + fileName);
	}
}
