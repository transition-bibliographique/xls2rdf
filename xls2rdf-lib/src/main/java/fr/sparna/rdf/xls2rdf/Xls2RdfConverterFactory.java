package fr.sparna.rdf.xls2rdf;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.rdf4j.repository.Repository;

import fr.sparna.rdf.xls2rdf.reconcile.SparqlReconcileService;

public class Xls2RdfConverterFactory {

	private boolean applyPostProcessings = true;
	private boolean generateXl = false;
	private boolean generateXlDefinitions = false;
	private Repository supportRepository = null;
	
	public Xls2RdfConverterFactory(boolean applyPostProcessings, boolean generateXl, boolean generateXlDefinitions) {
		super();
		this.applyPostProcessings = applyPostProcessings;
		this.generateXl = generateXl;
		this.generateXlDefinitions = generateXlDefinitions;
	}
	
	public Xls2RdfConverter newConverter(Repository outputRepository, String lang) {
		return this.newConverter(new RepositoryModelWriter(outputRepository), lang);
	}
	
	/**
	 * @param modelWriter
	 * @param lang can be null
	 * @return
	 */
	public Xls2RdfConverter newConverter(ModelWriterIfc modelWriter, String lang) {
		Xls2RdfConverter converter = new Xls2RdfConverter(modelWriter, lang);
		if(this.applyPostProcessings) {
			List<Xls2RdfPostProcessorIfc> postProcessors = new ArrayList<>();
			postProcessors.add(new SkosPostProcessor());
			if(this.generateXl || this.generateXlDefinitions) {
				postProcessors.add(new SkosXlPostProcessor(generateXl, generateXlDefinitions));
			}
			converter.setPostProcessors(postProcessors);
		}
		
		if(this.supportRepository != null) {
			converter.setReconcileService(new SparqlReconcileService(this.supportRepository));
		}
		
		return converter;
	}

	public boolean isApplyPostProcessings() {
		return applyPostProcessings;
	}

	public void setApplyPostProcessings(boolean applyPostProcessings) {
		this.applyPostProcessings = applyPostProcessings;
	}

	public boolean isGenerateXl() {
		return generateXl;
	}

	public void setGenerateXl(boolean generateXl) {
		this.generateXl = generateXl;
	}

	public boolean isGenerateXlDefinitions() {
		return generateXlDefinitions;
	}

	public void setGenerateXlDefinitions(boolean generateXlDefinitions) {
		this.generateXlDefinitions = generateXlDefinitions;
	}

	public Repository getSupportRepository() {
		return supportRepository;
	}

	public void setSupportRepository(Repository supportRepository) {
		this.supportRepository = supportRepository;
	}
	
	
	
}
