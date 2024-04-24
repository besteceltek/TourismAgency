package View;

import Business.HotelManager;
import Business.PensionManager;
import Business.SeasonManager;
import Core.Cities;
import Core.ComboItem;
import Core.Helper;
import Entity.Hotel;
import Entity.Pension;
import Entity.Season;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.Collator;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class HotelView extends Layout {
    private JPanel container;
    private JPanel pnl_hotel_features;
    private JPanel pnl_hotel_contact;
    private JPanel pnl_hotel_info;
    private JPanel pnl_button;

    private JLabel lbl_hotel;
    private JLabel lbl_hotel_name;
    private JLabel lbl_hotel_star;
    private JLabel lbl_hotel_city;
    private JLabel lbl_hotel_address;
    private JLabel lbl_hotel_region;
    private JLabel lbl_hotel_mail;
    private JLabel lbl_hotel_phone;
    private JLabel lbl_hotel_features;
    private JLabel lbl_pension_types;
    private JLabel lbl_hotel_season;

    private JTextField fld_hotel_name;
    private JTextField fld_hotel_mail;
    private JTextField fld_hotel_phone;
    private JTextArea txta_hotel_address;

    private JComboBox cmb_hotel_star;
    private JComboBox cmb_hotel_city;
    private JComboBox cmb_hotel_region;

    private JCheckBox chk_parking;
    private JCheckBox chk_wifi;
    private JCheckBox chk_pool;
    private JCheckBox chk_fitness;
    private JCheckBox chk_concierge;
    private JCheckBox chk_spa;
    private JCheckBox chk_room_service;
    private JCheckBox chk_ultra;
    private JCheckBox chk_all;
    private JCheckBox chk_room_brk;
    private JCheckBox chk_full;
    private JCheckBox chk_half;
    private JCheckBox chk_bed;
    private JCheckBox chk_excl_alch;
    private JCheckBox chk_summer;
    private JCheckBox chk_winter;

    private JButton btn_save;

    private Hotel hotel;
    private HotelManager hotelManager;
    private PensionManager pensionManager;
    private SeasonManager seasonManager;

    private final ArrayList<String> hotelFeaturesList = new ArrayList<>();
    private final ArrayList<String> hotelPensionList = new ArrayList<>();
    private final ArrayList<String> hotelSeasonList = new ArrayList<>();

    public HotelView(Hotel hotel) {
        this.add(container);
        this.initializeGui(550, 450);
        this.hotel = hotel;
        this.hotelManager = new HotelManager();
        this.pensionManager = new PensionManager();
        this.seasonManager = new SeasonManager();

        this.loadFeaturesCheckBoxListener();
        this.loadPensionCheckBoxListener();
        this.loadSeasonCheckBoxListener();
        this.loadComboCities(cmb_hotel_city);

        this.cmb_hotel_star.setModel(new DefaultComboBoxModel<>(new Object[]{1, 2, 3, 4, 5}));

        this.cmb_hotel_city.addActionListener(e -> {
            loadComboZones(cmb_hotel_city, cmb_hotel_region);
        });


        this.btn_save.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_hotel_name, this.fld_hotel_phone, this.fld_hotel_mail}) ||
                    Helper.isComboBoxListEmpty(new JComboBox[]{this.cmb_hotel_star, this.cmb_hotel_city, this.cmb_hotel_region})) {
                Helper.showMessage("fill");
            } else {
                this.hotel.setHotelName(fld_hotel_name.getText());
                this.hotel.setHotelCity(cmb_hotel_city.getSelectedItem().toString());
                this.hotel.setHotelRegion(cmb_hotel_region.getSelectedItem().toString());
                this.hotel.setHotelAddress(txta_hotel_address.getText());
                this.hotel.setHotelMail(fld_hotel_mail.getText());
                this.hotel.setHotelPhone(fld_hotel_phone.getText());
                this.hotel.setHotelStar((Integer) cmb_hotel_star.getSelectedItem());
                this.hotel.setHotelFeatures(this.hotelManager.arrayConversion(this.hotelFeaturesList));

                int hotelId = this.hotelManager.saveAndGetHotelId(this.hotel);

                // Add Pension
                savePension(hotelId);

                // Add season
                saveSeason(hotelId);

                Helper.showMessage("done");
                dispose();
            }
        });
    }

    public void savePension(int hotelId) {
        for (String pensionType : hotelPensionList) {
            Pension pension = new Pension();
            pension.setPensionType(pensionType);
            pension.setHotelId(hotelId);
            this.pensionManager.save(pension);
        }
    }

    public void saveSeason(int hotelId) {
        for (String seasonName : hotelSeasonList) {
            Season season = new Season();
            if (seasonName.equals("Winter")) {
                season.setSeasonName(seasonName);
                season.setSeasonStartDate(LocalDate.parse("01.10.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                season.setSeasonEndDate(LocalDate.parse("30.03.2025", DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                season.setHotelId(hotelId);
            } else if (seasonName.equals("Summer")) {
                season.setSeasonName(seasonName);
                season.setSeasonStartDate(LocalDate.parse("01.04.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                season.setSeasonEndDate(LocalDate.parse("30.09.2024", DateTimeFormatter.ofPattern("dd.MM.yyyy")));
                season.setHotelId(hotelId);
            }
            this.seasonManager.save(season);
        }
    }

    public void loadSeasonCheckBoxListener() {
        this.chk_winter.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hotelSeasonList.add(chk_winter.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hotelSeasonList.remove(chk_winter.getText());
                }
            }
        });

        this.chk_summer.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hotelSeasonList.add(chk_summer.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hotelSeasonList.remove(chk_summer.getText());
                }
            }
        });
    }

    public void loadPensionCheckBoxListener() {
        this.chk_ultra.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hotelPensionList.add(chk_ultra.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hotelPensionList.remove(chk_ultra.getText());
                }
            }
        });
        this.chk_all.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hotelPensionList.add(chk_all.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hotelPensionList.remove(chk_all.getText());
                }
            }
        });
        this.chk_room_brk.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hotelPensionList.add(chk_room_brk.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hotelPensionList.remove(chk_room_brk.getText());
                }
            }
        });
        this.chk_full.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hotelPensionList.add(chk_full.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hotelPensionList.remove(chk_full.getText());
                }
            }
        });
        this.chk_half.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hotelPensionList.add(chk_half.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hotelPensionList.remove(chk_half.getText());
                }
            }
        });
        this.chk_bed.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hotelPensionList.add(chk_bed.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hotelPensionList.remove(chk_bed.getText());
                }
            }
        });
        this.chk_excl_alch.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hotelPensionList.add(chk_excl_alch.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hotelPensionList.remove(chk_excl_alch.getText());
                }
            }
        });
    }

    public void loadFeaturesCheckBoxListener() {
        this.chk_parking.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hotelFeaturesList.add(chk_parking.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hotelFeaturesList.remove(chk_parking.getText());
                }
            }
        });
        this.chk_wifi.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hotelFeaturesList.add(chk_wifi.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hotelFeaturesList.remove(chk_wifi.getText());
                }
            }
        });
        this.chk_pool.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hotelFeaturesList.add(chk_pool.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hotelFeaturesList.remove(chk_pool.getText());
                }
            }
        });
        this.chk_fitness.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hotelFeaturesList.add(chk_fitness.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hotelFeaturesList.remove(chk_fitness.getText());
                }
            }
        });
        this.chk_concierge.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hotelFeaturesList.add(chk_concierge.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hotelFeaturesList.remove(chk_concierge.getText());
                }
            }
        });
        this.chk_spa.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hotelFeaturesList.add(chk_spa.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hotelFeaturesList.remove(chk_spa.getText());
                }
            }
        });
        this.chk_room_service.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    hotelFeaturesList.add(chk_room_service.getText());
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    hotelFeaturesList.remove(chk_room_service.getText());
                }
            }
        });
    }
}
