package net.alteiar.dao.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import net.alteiar.basictypes.BasicObject;
import net.alteiar.db.dao.Dao;
import net.alteiar.db.dao.exception.DataException;

public class XmlDao<E extends BasicObject> extends Dao {

	private String dir;

	protected JAXBContext jaxbContext;

	private long nextId;

	private Class<E> classe;

	protected void initialize(Class<E> classe) throws JAXBException {

		this.classe = classe;

		jaxbContext = JAXBContext.newInstance(classe);

		// find last id
		File f = new File(getParentDir());

		String prefix = classe.getSimpleName();

		Long lastId = -1L;

		for (String file : f.list()) {

			if (file.startsWith(prefix)) {

				long id = getId(file);

				if (id > lastId) {
					lastId = id;
				}
			}
		}

		nextId = lastId;
	}

	public void setParentDir(String dir) {

		this.dir = dir;
	}

	protected String getParentDir() {

		return this.dir;
	}

	protected long getId(String file) {

		String prefix = classe.getSimpleName();
		// getParentDir() + File.separator + classe.getSimpleName();

		file = file.replaceFirst(prefix, "");
		file = file.replaceFirst(".xml", "");

		return Long.valueOf(file);
	}

	protected synchronized long nextId() {

		// increase the id
		nextId++;
		return nextId;
	}

	protected String getFilename(Long id) {
		String filename = getParentDir() + File.separator + classe.getSimpleName() + id + ".xml";

		return filename;
	}

	protected boolean exist(E object) {

		File f = new File(getFilename(object));
		return f.exists();
	}

	protected boolean exist(Long id) {

		File f = new File(getFilename(id));
		return f.exists();
	}

	protected String getFilename(E object) {

		return getFilename(object.getId());
	}

	protected void deleteFile(E object) throws DataException {

		File f = new File(getFilename(object));

		if (!f.delete()) {

			String errorMsg = String.format("Fail to delete the object {%s} with id {%s}", object.getClass()
					.getSimpleName(), object.getId());
			throw new DataException(errorMsg);
		}
	}

	protected void saveFile(JAXBElement<E> object) throws DataException {

		OutputStream output = null;
		try {

			Marshaller marshaller = jaxbContext.createMarshaller();

			marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			String filename = getFilename(object.getValue());
			output = new FileOutputStream(filename);
			marshaller.marshal(object, output);

		} catch (JAXBException | FileNotFoundException e) {

			String errorMsg = String.format("Fail to save the object {%s} with id {%s}", object.getDeclaredType()
					.getSimpleName(), object.getValue().getId());
			throw new DataException(errorMsg, e);
		} finally {
			Util.close(output);
		}
	}

	protected List<E> loadAll(Class<E> classe) throws DataException {

		List<E> found = new ArrayList<E>();

		File f = new File(getParentDir());

		String prefix = getParentDir() + File.separator + classe.getSimpleName();

		for (String file : f.list()) {

			if (file.startsWith(prefix)) {

				found.add(loadFile(getId(file)));
			}
		}

		return found;
	}

	protected E loadFile(Long id) throws DataException {

		FileInputStream inputStream = null;

		try {

			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			String filename = getFilename(id);
			inputStream = new FileInputStream(filename);

			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader(inputStream);

			return unmarshaller.unmarshal(reader, classe).getValue();

		} catch (FileNotFoundException | JAXBException | XMLStreamException e) {

			String errorMsg = String.format("Fail to load the combat unit {%s}", id);
			throw new DataException(errorMsg, e);
		} finally {
			Util.close(inputStream);
		}
	}
}
