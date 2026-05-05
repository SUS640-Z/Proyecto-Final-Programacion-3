package controllers;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import javax.swing.JOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import models.User;
import repository.UserRepository;
import views.LoginView;
import views.RegistroView;

public class RegistroController{
	
	private RegistroView view;
	private UserRepository repository;

	public RegistroController(RegistroView view) {
		this.view = view;
		this.repository = new UserRepository(); 
		registerListeners();
		eventoCerradoVentana();
	}
	
	private void registerListeners() {
		   view.getBtnRegistrar().addActionListener(e -> validarForm());

		   view.getBtnSelectImage().addActionListener(e -> view.chooseImage());
		   
		   eventosCampos();
		   regresarVentana();
	}
	
	private void eventoCerradoVentana() {
		view.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				int opcion = JOptionPane.showConfirmDialog(
		                 null,
		                 "¿Seguro que deseas cerrar la aplicación?",
		                 "Confirmar salida",
		                 JOptionPane.YES_NO_OPTION
		         );

		         if(opcion == JOptionPane.YES_OPTION){
		             view.dispose(); 
		         }
	        }
		});
	}

	  private void validarForm() {
	        view.resetearAvisos();
	        boolean valido = true;
	        
	        if (!verificarName()) valido = false; 
	        if (!verificarLastName()) valido = false; 
	        if (!verificarCorreo()) valido = false;
	        if (!verificarPassword()) valido = false;
	        if (!verificarConfirmarPassword()) valido = false;
	        if (!verificarImage()) valido = false; 

	        if (valido) {

	        	String imagePathString = saveImage();
	        	
	            User nuevoUsuario = new User(
	            	view.getTxtName().getText().trim(),
	            	view.getTxtLastName().getText().trim(),
	            	view.getTxtCorreo().getText().trim(),
	            	new String(view.getTxtContrasena().getPassword()),
	            	imagePathString 
	            );

	            try {
					repository.save(nuevoUsuario);

					JOptionPane.showMessageDialog(null, "Cuenta registrada");
					view.dispose();

					LoginView ventanaLogin = new LoginView();
					new LoginController(ventanaLogin); 
					
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error al guardar el usuario: " + e.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
				}
	        }
	    }

	   private String saveImage() {
		   try {
			   String original = view.getSelectedImagePath();
			   
			   if(original == null)
				   return null;
			   
			   File source = new File(original);
			   String extension = original.substring(original.lastIndexOf("."));
			   String newName = UUID.randomUUID() + extension;
			   String folder = "." + File.separator + "images";
			   File directory = new File(folder);
			   
			   if(!directory.exists()) {
				   directory.mkdir();
			   }
			   
			   Path destination = Paths.get(folder, newName);
			   Files.copy(source.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
			   
			   return destination.toString();
			   
		   } catch(Exception ex) {
			   ex.printStackTrace();
			   return null;
		   }
	   }

	   private boolean verificarImage() {
		   if(view.getSelectedImagePath() == null) {
			   view.getLblAvisoImage().setText("Seleccione una foto de perfil");
			   return false;
		   }
		   view.getLblAvisoImage().setText("");
		   return true;
	   }
	   
	   private boolean verificarName() {
	        if(view.getTxtName().getText().trim().equals("")) {
	            view.getLblAvisoName().setText("Nombre requerido");
	            view.getLblAvisoName().setFont(new Font("Arial", Font.ITALIC, 10));
	            return false;
	        }
	        return true;
	    }
	    
	    private boolean verificarLastName() {
	        if(view.getTxtLastName().getText().trim().equals("")) {
	        	view.getLblAvisoLastName().setText("Apellido requerido");
	        	view.getLblAvisoLastName().setFont(new Font("Arial", Font.ITALIC, 10));
	            return false;
	        }
	        return true;
	    }

	    private boolean verificarCorreo() {
	        if(view.getTxtCorreo().getText().trim().equals("")) {
	        	view.getLblAvisoCorreo().setText("Correo requerido");
	        	view.getLblAvisoCorreo().setFont(new Font("Arial", Font.ITALIC, 10));
	            return false;
	        }
	        return true;
	    }
	    
	    private boolean verificarPassword() {
	        if(new String(view.getTxtContrasena().getPassword()).trim().equals("")) {
	        	view.getLblAvisoContra().setText("Contrasena requerida");
	        	view.getLblAvisoContra().setFont(new Font("Arial", Font.ITALIC, 10));
	            return false;
	        }
	        return true;
	    }

	    private boolean verificarConfirmarPassword() {
	        if(new String(view.getTxtConfirmarContrasena().getPassword()).trim().equals("") && new String(view.getTxtConfirmarContrasena().getPassword()).equals(new String(view.getTxtContrasena().getPassword()))){
	        	view.getLblAvisoConfirmar().setText("Confirme su contrasena");
	        	view.getLblAvisoConfirmar().setFont(new Font("Arial", Font.ITALIC, 10));
	            return false;
	        }
	        return true;
	    }
	    
	    private void verificarInstaName() {
	    	view.getLblAvisoName().setText("");
      if(view.getTxtName().getText().trim().equals("")) {
    	  view.getLblAvisoName().setText("Nombres requerido");
    	  view.getLblAvisoName().setFont(new Font("Arial", Font.ITALIC, 10));
      }else {
      	if(view.getTxtName().getText().matches(".*\\d.*")) {
      		view.getLblAvisoName().setText("No debe contener numeros");
    	}
     		
      	view.getTxtName().addKeyListener(new KeyAdapter() {
      			public void keyTyped(KeyEvent e) {
      				if((Character.isDigit(e.getKeyChar()) || !Character.isAlphabetic(e.getKeyChar())) && !(e.getKeyChar() == ' ') ) {
      					e.consume();
      				}
      				
      				if(view.getTxtName().getText().length() > 25) {
      					view.getLblAvisoName().setText("No puedes tener mas 25 caractereres");
      					e.consume();
      				}
      				
      				char c = e.getKeyChar();
      				
      				if(Character.isLowerCase(c)) {
      					e.setKeyChar(Character.toUpperCase(c));
      				}
      			}
      		
      		});
      		
      }

  }
  
  private void verificarInstaLastName() {
	  view.getLblAvisoLastName().setText("");
	    if(view.getTxtLastName().getText().trim().equals("")) {
	    	view.getLblAvisoLastName().setText("Apellidos requerido");
	    	view.getLblAvisoLastName().setFont(new Font("Arial", Font.ITALIC, 10));
	    }else {
  		
  		if(view.getTxtLastName().getText().matches(".*\\d.*")) {
  			view.getLblAvisoLastName().setText("No debe contener numeros");
 		}
  		
  		view.getTxtLastName().addKeyListener(new KeyAdapter() {
  			public void keyTyped(KeyEvent e) {
  				if((Character.isDigit(e.getKeyChar()) || !Character.isAlphabetic(e.getKeyChar())) && !(e.getKeyChar() == ' ') ) {
  					e.consume();
  				}
  				
  				if(view.getTxtLastName().getText().length() > 25) {
  					view.getLblAvisoLastName().setText("No puedes tener mas 25 caractereres");
  					e.consume();
  				}
  				
  				char c = e.getKeyChar();
  				
  				if(Character.isLowerCase(c)) {
  					e.setKeyChar(Character.toUpperCase(c));
  				}
  			}
  		});
  		
  }
}
  
  private void verificarInstaCorreo() {
	  view.getLblAvisoCorreo().setText(" ");
	    	if(view.getTxtCorreo().getText().trim().equals("") ) {
	    		view.getLblAvisoCorreo().setText("Correo requerido");
	    		view.getLblAvisoCorreo().setFont(new Font("Arial", Font.ITALIC, 10));
	    	}
	    	if(!view.getTxtCorreo().getText().contains("@")) {
	    		view.getLblAvisoCorreo().setText("Correo Invalido");
	    		view.getLblAvisoCorreo().setFont(new Font("Arial", Font.ITALIC, 10));
	    	}
	    	
	    	view.getTxtCorreo().addKeyListener(new KeyAdapter() {
  			public void keyTyped(KeyEvent e) {
  				if(e.getKeyChar() == ' ') {
  					e.consume();
  				}
  				
  				if(view.getTxtCorreo().getText().length() > 50) {
  					view.getLblAvisoCorreo().setText("No puedes tener mas 50 caractereres");
  					e.consume();
  				}
  				
  				char c = e.getKeyChar();
  				
  				if(Character.isLowerCase(c)) {
  					e.setKeyChar(Character.toUpperCase(c));
  				}
  			}
  		
  		});
  }
  
  private void verificarInstaPassword() {
  		boolean mayuscula=false;
  		boolean numeros=false;
  		boolean longitud=false;
  		view.getLblAvisoContra().setText(" ");
	    	if(new String(view.getTxtContrasena().getPassword()).trim().equals("")) {
	    		view.getLblAvisoContra().setText("Contrasena requerida");
	    		view.getLblAvisoContra().setFont(new Font("Arial", Font.ITALIC, 10));
	    }else {
		   for(int i = 0; i < new String(view.getTxtContrasena().getPassword()).length(); i++) {
			   if (Character.isUpperCase(new String(view.getTxtContrasena().getPassword()).charAt(i))) {
		            mayuscula=true; 
		        }
			   
			   if(new String(view.getTxtContrasena().getPassword()).matches(".*\\d.*")) {
				   numeros=true;
			   }
			   if(new String(view.getTxtContrasena().getPassword()).trim().length() >= 8 ) {
				   longitud=true;
    		}
		   }
		   
		   if(!mayuscula) {
			   view.getLblAvisoContra().setText("Se necesita al menos una mayuscula");
		   }
		   
		   if(!numeros) {
			   view.getLblAvisoContra().setText("Se necesita al menos un numero");
		   }
		   
		   if(!longitud) {
			   view.getLblAvisoContra().setText("Debe contener almenos 8 caracteres");
		   }
	    }
	    	
	    	view.getTxtContrasena().addKeyListener(new KeyAdapter() {
  			public void keyTyped(KeyEvent e) {
  				if(e.getKeyChar() == ' ') {
  					e.consume();
  				}
  				
  				if((new String(view.getTxtContrasena().getPassword())).length() > 20) {
  					view.getLblAvisoContra().setText("No puedes tener mas 20 caractereres");
  					e.consume();
  				}
  				
  				char c = e.getKeyChar();
  				
  				if(Character.isLowerCase(c)) {
  					e.setKeyChar(Character.toUpperCase(c));
  				}
  			}
  		
  		});
	    	
  }
  
  private void verificarInstaConfiPassword() {
	  view.getLblAvisoConfirmar().setText(" ");
	    	if(new String(view.getTxtConfirmarContrasena().getPassword()).trim().equals("") || !(new String(view.getTxtConfirmarContrasena().getPassword()).equals(new String(view.getTxtContrasena().getPassword())))){
	    		view.getLblAvisoConfirmar().setText("Las contraseñas no son iguales");
	    		view.getLblAvisoConfirmar().setFont(new Font("Arial", Font.ITALIC, 10));  
	     }
  }
  
  private void eventosCampos(){
	  view.getTxtName().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				verificarInstaName();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				verificarInstaName();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				verificarInstaName();
			}
		});
		
		view.getTxtLastName().getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				verificarInstaLastName();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				verificarInstaLastName();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				verificarInstaLastName();
			}
		});

		
		view.getTxtCorreo().getDocument().addDocumentListener(new DocumentListener() {
		@Override
		public void removeUpdate(DocumentEvent e) {
			verificarInstaCorreo();
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			verificarInstaCorreo();
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			verificarInstaCorreo();
		}
	});
  
  view.getTxtContrasena().getDocument().addDocumentListener(new DocumentListener() {
		@Override
		public void removeUpdate(DocumentEvent e) {
			verificarInstaPassword();
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			verificarInstaPassword();
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			verificarInstaPassword();
		}
	});
  
  view.getTxtConfirmarContrasena().getDocument().addDocumentListener(new DocumentListener() {
		@Override
		public void removeUpdate(DocumentEvent e) {
			verificarInstaConfiPassword();
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			verificarInstaConfiPassword();
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			verificarInstaConfiPassword();
		}
	});
}
  
  public void regresarVentana() {
	  view.getLblRegresar().addMouseListener(new MouseAdapter() {
          public void mouseClicked(MouseEvent e) {
          	int opcion = JOptionPane.showConfirmDialog(
                      null,
                      "¿Seguro que deseas regresar?",
                      "Confirmar salida",
                      JOptionPane.YES_NO_OPTION
              );

              if(opcion == JOptionPane.YES_OPTION){ 
            	  LoginView ventanaLogin = new LoginView();
                  new controllers.LoginController(ventanaLogin); 
                  view.dispose(); 
              }
          }
	   });
  }
}